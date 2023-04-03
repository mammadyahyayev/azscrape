package az.my.datareport.services;

import az.my.datareport.config.Project;
import az.my.datareport.utils.PropertiesFileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ProjectServiceImpl implements ProjectService {
    private static final Logger LOG = LogManager.getLogger(ProjectServiceImpl.class);

    private final PropertiesFileSystem fileSystem;

    public ProjectServiceImpl(PropertiesFileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public void createProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null!");
        }

        String ymDir = System.getProperty(".ym");
        String projectDir = ymDir + File.separator + project.getName();
        fileSystem.createDirectoryIfNotExist(projectDir);
        LOG.info("Project {} directory created with {}", project.getName(), project);

        String propertiesFilePath = projectDir + File.separator + "project.properties";
        fileSystem.createFileIfNotExist(propertiesFilePath);
        LOG.info("Project {} properties file created with {}", project.getName(), propertiesFilePath);

        try (OutputStream outputStream = new FileOutputStream(propertiesFilePath)) {
            Properties properties = new Properties();
            properties.setProperty("project.name", project.getName());
            properties.setProperty("project.owner", project.getOwner().getName());
            properties.setProperty("project.createdAt", project.getCreatedAt().toString());

            properties.store(outputStream, "Project Properties");
            LOG.info("Configurations stored on properties {} file", propertiesFilePath);

            LOG.info("Project created successfully...");
        } catch (IOException e) {
            LOG.error("Failed to create project {}", e.getMessage());
            throw new RuntimeException(e); //TODO: Replace this
        }
    }
}
