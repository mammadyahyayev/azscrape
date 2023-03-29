package az.my.datareport.config;

import az.my.datareport.utils.FileManager;
import az.my.datareport.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Properties;

public class DataReportProjectConfiguration {
    private static final Logger LOG = LogManager.getLogger(DataReportProjectConfiguration.class);

    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path YM_DIRECTORY_PATH = Path.of(USER_HOME, ".ym");
    private static final Path YM_PROPERTIES_FILE_PATH = Path.of(YM_DIRECTORY_PATH.toString(), ".ym.properties");

    private final FileManager fileManager;

    public DataReportProjectConfiguration(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createYmDirectory() {
        if (fileManager.isDirectoryExist(YM_DIRECTORY_PATH.toString())) {
            return;
        }

        fileManager.createDirectory(YM_DIRECTORY_PATH.toString());
    }

    public void createYmPropertiesFile() {
        if (!fileManager.isDirectoryExist(YM_DIRECTORY_PATH.toString())) {
            createYmDirectory();
        }

        if (fileManager.isFileExist(YM_PROPERTIES_FILE_PATH.toString())) {
            return;
        }

        fileManager.createFile(YM_PROPERTIES_FILE_PATH.toString());
    }

    public void createProject(Project project) {
        if (!fileManager.isDirectoryExist(YM_DIRECTORY_PATH.toString())) {
            createYmDirectory();
        }

        if (fileManager.isFileExist(YM_PROPERTIES_FILE_PATH.toString())) {
            createYmPropertiesFile();
        }

        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null!");
        }

        try {
            Path projectFolderPath = Path.of(YM_DIRECTORY_PATH.toString(), project.getName());
            fileManager.createDirectory(projectFolderPath.toString());
            LOG.info("Project {} folder created {}", project.getName(), projectFolderPath);

            Path propertiesFilePath = Path.of(projectFolderPath.toString(), "project.properties");
            fileManager.createFile(propertiesFilePath.toString());
            LOG.info("Project {} properties file created {}", project.getName(), propertiesFilePath);

            storeProjectProperties(project, propertiesFilePath);
            LOG.info("Configurations stored on properties {} file", propertiesFilePath);

            LOG.info("Project created successfully...");
        } catch (IOException e) {
            LOG.error("Failed to create project {}", e.getMessage());
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    private void storeProjectProperties(Project project, Path propertiesFilePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(propertiesFilePath.toFile())) {
            Properties properties = new Properties();
            properties.setProperty("project.name", project.getName());
            properties.setProperty("project.owner", project.getOwner().getName());
            properties.setProperty("project.createdAt", project.getCreatedAt().toString());

            properties.store(outputStream, "Project Properties");
        }
    }

    public void createOwner(Owner owner) {
        if (!fileManager.isDirectoryExist(YM_DIRECTORY_PATH.toString())) {
            createYmDirectory();
        }

        if (fileManager.isFileExist(YM_PROPERTIES_FILE_PATH.toString())) {
            createYmPropertiesFile();
        }

        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null!");
        }

        try {
            storeOwnerProperties(owner);
            LOG.info("Owner created successfully...");
        } catch (IOException e) {
            LOG.error("Failed to create owner {}", e.getMessage());
            throw new RuntimeException(e); //TODO: Replace this exception with System exception
        }
    }

    private void storeOwnerProperties(Owner owner) throws IOException {
        try (OutputStream outputStream =
                     new FileOutputStream(DataReportProjectConfiguration.YM_PROPERTIES_FILE_PATH.toFile())) {
            Properties properties = new Properties();
            properties.setProperty("owner.name", owner.getName());

            if (!StringUtils.isNullOrEmpty(owner.getEmail())) {
                properties.setProperty("owner.email", owner.getEmail());
            }

            properties.store(outputStream, "Global Properties");
        }
    }
}
