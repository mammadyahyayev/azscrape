package az.my.datareport.constant;

import java.nio.file.Path;

public class TestConstants {
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final Path MAIN_RESOURCES = Path.of(USER_DIR, "src", "main", "resources");
    public static final Path TEST_RESOURCES = Path.of(USER_DIR, "src", "test", "resources");
    public static final Path CONFIG_FILE_PATH = Path.of(MAIN_RESOURCES.toString(), "config.json");
    public static final Path TEST_CONFIG_FILE_PATH = Path.of(TEST_RESOURCES.toString(), "test-config.json");
    public static final Path EMPTY_CONFIG_FILE_PATH = Path.of(TEST_RESOURCES.toString(), "empty-config.json");
}
