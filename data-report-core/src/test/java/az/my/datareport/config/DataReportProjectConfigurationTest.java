package az.my.datareport.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DataReportProjectConfigurationTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_FOLDER_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_FOLDER_PATH.toString(), ".ym.properties");

    @AfterAll
    static void cleanUp() throws IOException {
        Files.deleteIfExists(YM_PROPERTIES_FILE_PATH);
        Files.deleteIfExists(YM_FOLDER_PATH);
    }

    @Test
    void testCreateYmFolder() {
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.createYmFolder();

        File ymFolder = new File(YM_FOLDER_PATH.toString());
        assertTrue(ymFolder.exists());
    }

    @Test
    void testCreateYmPropertiesFile() {
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.createYmFolder();
        configuration.createYmPropertiesFile();

        File ymFolder = new File(YM_FOLDER_PATH.toString());
        File ymPropertiesFile = new File(YM_PROPERTIES_FILE_PATH.toString());

        assertTrue(ymFolder.exists());
        assertTrue(ymPropertiesFile.exists());
    }

    @Test
    void testCreateYmPropertiesWithYmFolderAutomatically() {
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.createYmPropertiesFile();

        File ymFolder = new File(YM_FOLDER_PATH.toString());
        File ymPropertiesFile = new File(YM_PROPERTIES_FILE_PATH.toString());

        assertTrue(ymFolder.exists());
        assertTrue(ymPropertiesFile.exists());
    }

}