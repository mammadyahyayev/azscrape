package az.caspian.core.service;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.io.DefaultFileSystem;
import az.caspian.core.io.PropertiesFileSystem;
import az.caspian.core.messaging.Client;
import az.caspian.core.messaging.JoinToProjectMessage;
import az.caspian.core.messaging.ShortMessage;
import az.caspian.core.remote.Project;
import az.caspian.core.remote.Session;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

public class ClientService {
  private static final Logger LOG = LogManager.getLogger(ClientService.class);

  private final ProjectService projectService;

  public ClientService(ProjectService projectService) {
    this.projectService = projectService;
  }

  public void handleShortMessage(ShortMessage message) {
    if (message instanceof JoinToProjectMessage joinToProjectMessage) {
      Client client = joinToProjectMessage.client();
      Project currentProject = Session.getCurrentProject();
      LOG.debug("Client ({}) is trying to connect to {}", client.getFullName(), currentProject.getName());
      joinToProject(client, currentProject);
    }
  }

  public void joinToProject(Client client, Project project) {
    Asserts.required(project, "project cannot be null!");
    Asserts.required(client, "client cannot be null!");

    project.addAttendant(client);
    projectService.saveAttendants(project);
  }

  /**
   * Creates <b>identity.properties</b> file and stores Client's information
   * on the file.
   *
   * @param client information of Client
   * @return {@code true} if operation successful, otherwise {@code false}.
   */
  public boolean saveClientInfo(Client client) {
    if (client == null)
      throw new IllegalArgumentException("Client can't be null");

    try {
      var properties = new Properties();
      properties.put("fullName", client.getFullName());
      properties.put("email", client.getEmail());
      properties.put("os", System.getProperty("os.name"));

      if (client.getComputerDetails() != null) {
        Map<String, String> computerDetails = client.getComputerDetails();
        properties.put("computerBrand", computerDetails.get("computerBrand"));
        properties.put("ram", computerDetails.get("ram"));
      }

      properties.put("initialized", "true");
      var fileSystem = new PropertiesFileSystem();
      fileSystem.store(FileConstants.IDENTITY_FILE_PATH, properties);
    } catch (Exception ex) {
      LOG.error("Failed to write properties to file!, Ex: {}", ex.getMessage());
      return false;
    }

    return true;
  }

  /**
   * Checks whether Client's identity is introduced to the system or not.
   *
   * @return {@code true} if <b>identity.properties</b> file exists and client information
   * is listed on the file, otherwise {@code false}
   */
  public boolean isClientInitialized() {
    var fileSystem = new PropertiesFileSystem();
    boolean isExists = fileSystem.isFileExist(FileConstants.IDENTITY_FILE_PATH);
    if (isExists) {
      Properties properties = fileSystem.load(FileConstants.IDENTITY_FILE_PATH);
      return Boolean.parseBoolean((String) properties.get("initialized"));
    }

    return false;
  }

  public boolean createProject(String projectName, Client client) {
    Asserts.required(projectName, "projectName is required parameter!");
    Path projectPath = FileConstants.APP_PATH.resolve(projectName);

    var fileSystem = new DefaultFileSystem();
    fileSystem.createDirectoryIfNotExist(projectPath);
    LOG.debug("Project " + projectName + " folder is created.");
    LOG.debug("Setting up configurations...");

    var propertiesFileSystem = new PropertiesFileSystem();
    var projectPropertiesFilePath = projectPath.resolve("project.properties");

    var properties = new Properties();
    properties.put("projectName", projectName);
    properties.put("createdAt", DateUtils.ofDefaultFormat(LocalDateTime.now()));
    properties.put("createdBy", client.getFullName());
    propertiesFileSystem.store(projectPropertiesFilePath, properties);
    LOG.debug("project.properties file is created.");

    var attendantsFilePath = projectPath.resolve("attendants.txt");
    File attendantsFile = fileSystem.createFileIfNotExist(attendantsFilePath);
    LOG.debug("attendants file is created.");

    String attendant = client.getFullName();
    try (FileWriter fileWriter = new FileWriter(attendantsFile)) {
      fileWriter.write(attendant);
    } catch (IOException ex) {
      LOG.error("Failed to write into attendants.txt file!");
      //TODO: Think how to handle it on the UI.
      return false;
    }

    return true;
  }
}
