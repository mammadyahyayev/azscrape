package az.my.datareport.services;

import az.my.datareport.config.Project;
import az.my.datareport.utils.PropertiesFileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
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

        Properties properties = fileSystem.load(propertiesFilePath);
        properties.setProperty("project.name", project.getName());
        properties.setProperty("project.owner", project.getOwner().getName());
        properties.setProperty("project.owner.email", project.getOwner().getEmail());
        properties.setProperty("project.createdAt", project.getCreatedAt().toString());
        fileSystem.store(propertiesFilePath, properties);

        LOG.info("Configurations stored on properties {} file", propertiesFilePath);

        LOG.info("Project created successfully...");
    }
}
