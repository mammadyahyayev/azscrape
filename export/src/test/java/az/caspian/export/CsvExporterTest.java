package az.caspian.export;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.DataRow;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.tree.DataTable;
import org.apache.commons.io.file.DeletingPathVisitor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

  private static final String TEST_RESOURCE_FOLDER_PATH = "src/test/resources";

  @Test
  void testExportDataTableToCsvFile() throws IOException {
    CsvExporter exporter = new CsvExporter();

    DataTable table = getDataTable();

    var dataFile =
        new DataFile.Builder()
            .filename("test")
            .fileType(FileType.CSV)
            .storeAt(TEST_RESOURCE_FOLDER_PATH)
            .build();

    exporter.export(dataFile, table);

    Path path = Path.of("src/test/resources/test.csv");
    assertTrue(Files.exists(path));
    List<String> lines = Files.readAllLines(path);

    assertEquals(table.rows().size() + 1, lines.size(), "Expected number of lines in the CSV file");
  }

  @NotNull
  private static DataTable getDataTable() {
    DataTable table = new DataTable();

    List<DataRow> rows = new ArrayList<>();

    var row1 = new DataRow();
    row1.addColumn(new DataColumn("id", "1"));
    row1.addColumn(new DataColumn("name", "Jack"));
    row1.addColumn(new DataColumn("surname", "Jackson"));
    rows.add(row1);

    var row2 = new DataRow();
    row2.addColumn(new DataColumn("id", "2"));
    row2.addColumn(new DataColumn("name", "Rose"));
    row2.addColumn(new DataColumn("surname", "Smith"));
    rows.add(row2);

    table.addAll(rows);
    return table;
  }

  @Test
  void throwException_whenGivenFileTypeIsNotCsv() {
    DataFile dataFile = new DataFile.Builder().filename("test").fileType(FileType.EXCEL).build();

    assertThrows(
        IllegalStateException.class, () -> new CsvExporter().export(dataFile, new DataTable()));
  }

  @AfterAll
  static void afterAll() throws IOException {
    Files.walkFileTree(Path.of(TEST_RESOURCE_FOLDER_PATH), DeletingPathVisitor.withLongCounters());
  }
}
