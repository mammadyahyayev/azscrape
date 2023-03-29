package az.my.datareport.config;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataReportProjectConfigurationTest {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_FOLDER_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_FOLDER_PATH.toString(), ".ym.properties");

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(YM_PROPERTIES_FILE_PATH);
        FileUtils.deleteDirectory(YM_FOLDER_PATH.toFile());
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

    @Test
    void testCreateProject() {
        Project project = new Project();
        project.setName("demo-project");
        project.setOwner(new Owner("Jack", "jack@gmail.com"));

        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.createProject(project);

        File projectDirPath = new File(YM_FOLDER_PATH + "/" + project.getName());
        assertTrue(projectDirPath.exists());
        assertTrue(projectDirPath.isDirectory());

        File projectPropertiesPath = new File(projectDirPath.getAbsolutePath() + "/project.properties");
        assertTrue(projectPropertiesPath.exists());
        assertTrue(projectPropertiesPath.isFile());
    }

    @Test
    void testCreateExistProject() throws Exception {
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.createYmFolder();

        String projectName = "demo-project";
        Path projectDirPath = Path.of(YM_FOLDER_PATH.toString(), projectName);
        Files.createDirectory(projectDirPath);
        Files.createFile(Path.of(projectDirPath.toString(), "project.properties"));

        Project project = new Project();
        project.setName(projectName);
        project.setOwner(new Owner("Jack", "jack@gmail.com"));
        assertThrows(RuntimeException.class, () -> configuration.createProject(project));
    }

}