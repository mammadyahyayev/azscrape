package az.caspian.scrape.strategy;

import az.caspian.core.AzScrapeAppException;
import az.caspian.core.constant.FileConstants;
import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.serialization.ObjectDeserializer;
import az.caspian.core.serialization.ObjectSerializer;
import az.caspian.core.tree.DataTable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaveDataToFileStrategyTest {

  DataHandlingStrategy strategy;

  @Test
  void testSavingDataToFileProcess(@TempDir Path folderPath) throws IOException {
    // given
    var data = getData();
    strategy = new TestSaveDataToFileStrategy(folderPath, "name_surname");

    //when
    strategy.handle(data);

    //then
    assertTrue(Files.exists(folderPath));
    Path filePath = folderPath.resolve("backup_name_surname.ser");
    assertTrue(Files.exists(filePath));

    // deserialize data and compare it with given data
    byte[] bytes;
    try (var inputStream = new FileInputStream(filePath.toFile())) {
      bytes = inputStream.readAllBytes();
    }
    Object deserializedObject = ObjectDeserializer.deserialize(bytes);
    assertInstanceOf(DataTable.class, deserializedObject);
    DataTable savedData = (DataTable) deserializedObject;

    assertNotNull(savedData);
    assertEquals(2, savedData.rows().size());
    assertTrue(savedData.rows().contains(data.rows().get(0)));

    clean(List.of(filePath, folderPath));
  }

  @Test
  void throwExceptionWhenGivenDataIsNull() {
    var saveDataToFileStrategy = new SaveDataToFileStrategy();
    assertThrows(IllegalArgumentException.class,
      () -> saveDataToFileStrategy.handle(null));
  }

  private void clean(List<Path> paths) {
    for (Path path : paths) {
      try {
        if (Files.exists(path)) {
          Files.delete(path);
          System.out.println("Deleted: " + path);
        }
      } catch (IOException e) {
        System.err.println("Error deleting " + path + ": " + e.getMessage());
      }
    }
  }

  private DataTable getData() {
    var table = new DataTable();
    var jack = new DataRow();
    jack.addColumn(new DataColumn("name", "Jack"));
    jack.addColumn(new DataColumn("surname", "Jackson"));

    var smith = new DataRow();
    smith.addColumn(new DataColumn("name", "Smith"));
    smith.addColumn(new DataColumn("surname", "Doe"));

    table.addAll(List.of(jack, smith));
    return table;
  }


  static class TestSaveDataToFileStrategy extends SaveDataToFileStrategy {
    private final Path backupFolderPath;
    private final String fileUniqueIdentifier;

    TestSaveDataToFileStrategy(Path backupFolderPath, String fileUniqueIdentifier) {
      this.backupFolderPath = backupFolderPath;
      this.fileUniqueIdentifier = fileUniqueIdentifier;
    }

    @Override
    public void handle(DataTable data) {
      if (data == null) {
        throw new IllegalArgumentException("Can't save null data to file");
      }

      try {
        if (!Files.exists(backupFolderPath)) {
          Files.createDirectory(FileConstants.BACKUP_FILES_PATH);
        }
      } catch (IOException ex) {
        throw new AzScrapeAppException("Failed to create backups folder!", ex);
      }

      final String filename = "backup_" + fileUniqueIdentifier + SERIALIZED_FILE_EXTENSION;
      final Path filepath = backupFolderPath.resolve(filename);

      try {
        if (!Files.exists(filepath)) {
          Files.createFile(filepath);
        }
      } catch (IOException ex) {
      }

      byte[] serializedData = ObjectSerializer.serialize(data);
      if (serializedData == null) {
        return;
      }

      try (var fileOutputStream = new FileOutputStream(filepath.toString())) {
        fileOutputStream.write(serializedData);
      } catch (IOException ex) {
      }
    }
  }
}