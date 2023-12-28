package az.caspian.scrape.strategy;

import az.caspian.core.AzScrapeAppException;
import az.caspian.core.constant.FileConstants;
import az.caspian.core.serialization.ObjectSerializer;
import az.caspian.core.tree.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class SaveDataToFileStrategy implements DataHandlingStrategy {
  private static final Logger LOG = LogManager.getLogger(SaveDataToFileStrategy.class);

  @Override
  public void handle(DataTable data) {
    try {
      if (!Files.exists(FileConstants.BACKUP_FILES_PATH)) {
        Files.createDirectory(FileConstants.BACKUP_FILES_PATH);
        LOG.debug("backups folder is created...");
      }
    } catch (IOException ex) {
      LOG.error("Failed to create backups folder!");
      throw new AzScrapeAppException("Failed to create backups folder!", ex);
    }

    final String filename = "backup_" + Instant.now() + SERIALIZED_FILE_EXTENSION;
    final Path filepath = FileConstants.BACKUP_FILES_PATH.resolve(filename);

    try {
      if (!Files.exists(filepath)) {
        Files.createFile(filepath);
        LOG.debug("Backup file: {} created", filepath);
      }
    } catch (IOException ex) {
      LOG.error("Failed to create backup file: {}", filepath);
    }

    if (data == null) {
      throw new IllegalArgumentException("Can't save null data to %s file".formatted(filepath));
    }

    byte[] serializedData = ObjectSerializer.serialize(data);
    if (serializedData == null) {
      LOG.debug("Backup process is skipped because serialized data is null!");
      return;
    }

    try (var fileOutputStream = new FileOutputStream(filepath.toString())) {
      fileOutputStream.write(serializedData);
      LOG.debug("Serialized data has been written to file {}", filepath);
    } catch (IOException ex) {
      LOG.error("Failed to write serialized data to file {}", filepath);
      LOG.error("Exception: {}\nStack Trace: {}", ex.getMessage(), ex);
      LOG.debug("Backup process is skipped because writing serialized data to file process is failed");
    }
  }
}
