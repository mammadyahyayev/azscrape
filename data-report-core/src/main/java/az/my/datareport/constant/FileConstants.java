package az.my.datareport.constant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConstants {
    public static final String TEMP_DIR_PATH = getTempDirPath();

    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String MAIN_RESOURCES = Path.of("src", "main", "resources").toString();
    public static final String TEST_RESOURCES = Path.of(USER_DIR, "src", "test", "resources").toString();
    public static final String CONFIG_FILE_PATH = Path.of(MAIN_RESOURCES, "config.json").toString();
    private static final String MODULE_PARENT_PATH = Paths.get(System.getProperty("user.dir")).getParent().toString();
    // MODULE Paths
    public static final String MODULE_CORE_PATH = Path.of(MODULE_PARENT_PATH, "data-report-core").toString(); // TODO: Create enum for modules and restrict access

    private static String getTempDirPath() {
        try {
            Path tempDir = Files.createTempDirectory("__exported_files__");
            return tempDir.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp directory: " + e.getMessage());
        }
    }

}
