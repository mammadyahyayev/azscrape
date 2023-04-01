package az.my.datareport.config;

import az.my.datareport.utils.AbstractFileSystem;
import az.my.datareport.utils.DefaultFileSystem;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DataReportProjectConfigurationTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_DIRECTORY_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_DIRECTORY_PATH.toString(), ".ym.properties");

    private AbstractFileSystem abstractFileSystem;

    @BeforeEach
    void setUp() {
        abstractFileSystem = new DefaultFileSystem();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(YM_PROPERTIES_FILE_PATH);
        FileUtils.deleteDirectory(YM_DIRECTORY_PATH.toFile());
    }
}