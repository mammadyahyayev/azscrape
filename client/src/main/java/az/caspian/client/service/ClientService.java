package az.caspian.client.service;

import az.caspian.client.ClientConnection;
import az.caspian.core.constant.FileConstants;
import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.*;
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

  private final ClientConnection connection;

  public ClientService(ClientConnection connection) {
    this.connection = connection;
  }

  public void joinToProject(final String projectId, ClientInfo clientInfo) {
    if (StringUtils.isNullOrEmpty(projectId)) {
      throw new IllegalArgumentException("projectId cannot be null or empty!");
    }

    if (clientInfo == null) {
      throw new IllegalArgumentException("ClientInfo cannot be null!");
    }

    try {
      connection.joinToProject(projectId, clientInfo);
    } catch (IOException | InterruptedException e) {
      LOG.error("{} is failed to join to project: '{}'", clientInfo.getFullName(), projectId);
    }
  }

  /**
   * Creates <b>identity.properties</b> file and stores Client's information
   * on the file.
   *
   * @param clientInfo information of Client
   * @return {@code true} if operation successful, otherwise {@code false}.
   */
  public boolean saveClientInfo(ClientInfo clientInfo) {
    if (clientInfo == null)
      throw new IllegalArgumentException("ClientInfo can't be null");

    try {
      var properties = new Properties();
      properties.put("fullName", clientInfo.getFullName());
      properties.put("email", clientInfo.getEmail());
      properties.put("os", System.getProperty("os.name"));

      if (clientInfo.getComputerDetails() != null) {
        Map<String, String> computerDetails = clientInfo.getComputerDetails();
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
    boolean isExists = fileSystem.isFileExist(FileConstants.IDENTITY_FILE_PATH.toString());
    if (isExists) {
      Properties properties = fileSystem.load(FileConstants.IDENTITY_FILE_PATH);
      return Boolean.parseBoolean((String) properties.get("initialized"));
    }

    return false;
  }

  public boolean createProject(String projectName, ClientInfo clientInfo) {
    Asserts.required(projectName, "projectName is required parameter!");
    Path projectPath = FileConstants.APP_PATH.resolve(projectName);

    var fileSystem = new DefaultFileSystem();
    fileSystem.createDirectoryIfNotExist(projectPath.toString());
    LOG.debug("Project " + projectName + " folder is created.");
    LOG.debug("Setting up configurations...");

    var propertiesFileSystem = new PropertiesFileSystem();
    var projectPropertiesFilePath = projectPath.resolve("project.properties");

    var properties = new Properties();
    properties.put("projectName", projectName);
    properties.put("createdAt", DateUtils.ofDefaultFormat(LocalDateTime.now()));
    properties.put("createdBy", clientInfo.getFullName());
    propertiesFileSystem.store(projectPropertiesFilePath, properties);
    LOG.debug("project.properties file is created.");

    var attendantsFilePath = projectPath.resolve("attendants.txt");
    File attendantsFile = fileSystem.createFileIfNotExist(attendantsFilePath.toString());
    LOG.debug("attendants file is created.");

    String attendant = clientInfo.getFullName();
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
