package az.my.datareport.constant;

import java.nio.file.Path;

public class FileConstants {
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String MAIN_RESOURCES = Path.of("src", "main", "resources").toString();
    public static final String TEST_RESOURCES = Path.of(USER_DIR, "src", "test", "resources").toString();
    public static final String CONFIG_FILE_PATH = Path.of(MAIN_RESOURCES, "config.json").toString();

    // MODULE Paths
    public static final String MODULE_CORE_PATH = Path.of(USER_DIR, "data-report-core").toString(); // TODO: Create enum for modules and restrict access
}
