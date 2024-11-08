package az.caspian.core.constant;

import az.caspian.core.AzScrapeAppException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileConstants {
  public static final Path APP_PATH = Path.of(System.getProperty("user.home"), ".azscrape");
  public static final Path BACKUP_FILES_PATH = APP_PATH.resolve("backups");
  public static final Path IDENTITY_FILE_PATH = APP_PATH.resolve("identity.properties");
  public static final Path SHARED_PROJECT_FILE_PATH = APP_PATH.resolve("shared-project.txt");

  public static final String TEMP_DIR_PATH = getTempDirPath();

  public static final String USER_DIR = System.getProperty("user.dir");
  public static final String MAIN_RESOURCES = Path.of("src", "main", "resources").toString();
  public static final String TEST_RESOURCES = Path.of(USER_DIR, "src", "test", "resources").toString();
  public static final String CONFIG_FILE_PATH = Path.of(MAIN_RESOURCES, "config.json").toString();

  // MODULE Paths
  private static String getTempDirPath() {
    try {
      Path tempDir = Files.createTempDirectory("__exported_files__");
      return tempDir.toString();
    } catch (IOException e) {
      throw new AzScrapeAppException("Failed to create temp directory: " + e.getMessage());
    }
  }

  private FileConstants() {

  }

}
