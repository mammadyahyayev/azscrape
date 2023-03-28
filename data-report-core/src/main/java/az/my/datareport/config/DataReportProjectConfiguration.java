package az.my.datareport.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class DataReportProjectConfiguration {
    private static final Logger LOG = LogManager.getLogger(DataReportProjectConfiguration.class);

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_FOLDER_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_FOLDER_PATH.toString(), ".ym.properties");

    public void createYmFolder() {
        if (checkYmFolderExist()) {
            return;
        }

        try {
            Files.createDirectory(YM_FOLDER_PATH);
        } catch (IOException e) {
            LOG.error("Failed to create .ym folder inside {}", USER_HOME);
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    public void createYmPropertiesFile() {
        if (!checkYmFolderExist()) {
            createYmFolder();
        }

        if (checkYmPropertiesFileExist()) {
            return;
        }

        try {
            Files.createFile(YM_PROPERTIES_FILE_PATH);
        } catch (IOException e) {
            LOG.error("Failed to create .ym.properties inside {}", YM_FOLDER_PATH);
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    public void createProject(Project project) {
        try {
            Path projectFolderPath = Path.of(YM_FOLDER_PATH.toString(), project.getName());
            Files.createDirectory(projectFolderPath);
            LOG.info("Project {} folder created {}", project.getName(), projectFolderPath);

            Path propertiesFilePath = Path.of(projectFolderPath.toString(), "project.properties");
            Files.createFile(propertiesFilePath);
            LOG.info("Project {} properties file created {}", project.getName(), propertiesFilePath);

            storeProperties(project, propertiesFilePath);
            LOG.info("Configurations stored on properties {} file", propertiesFilePath);

            LOG.info("Project created successfully...");
        } catch (IOException e) {
            LOG.error("Failed to create project {}", e.getMessage());
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    private void storeProperties(Project project, Path propertiesFilePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(propertiesFilePath.toFile())) {
            Properties properties = new Properties();
            properties.setProperty("project.name", project.getName());
            properties.setProperty("project.owner", project.getOwner().getName());
            properties.setProperty("project.createdAt", project.getCreatedAt().toString());

            properties.store(outputStream, "Project Properties");
        }
    }

    private boolean checkYmFolderExist() {
        File file = new File(YM_FOLDER_PATH.toString());
        return file.exists();
    }

    private boolean checkYmPropertiesFileExist() {
        File file = new File(YM_PROPERTIES_FILE_PATH.toString());
        return file.exists();
    }

}
