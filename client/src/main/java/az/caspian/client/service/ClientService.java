package az.caspian.client.service;

import az.caspian.client.ClientConnection;
import az.caspian.core.constant.FileConstants;
import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.PropertiesFileSystem;
import az.caspian.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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

  public boolean isClientInitialized() {
    var fileSystem = new PropertiesFileSystem();
    boolean isExists = fileSystem.isFileExist(FileConstants.IDENTITY_FILE_PATH.toString());
    if (isExists) {
      Properties properties = fileSystem.load(FileConstants.IDENTITY_FILE_PATH);
      return Boolean.parseBoolean((String) properties.get("initialized"));
    }

    return false;
  }

}
