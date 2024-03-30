package az.caspian.core.remote;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.io.PropertiesFileSystem;
import az.caspian.core.messaging.Client;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

public final class Session {
  private static final Logger LOG = LogManager.getLogger(Session.class);

  private static String serverIpAddress;
  private static Client currentClient;
  private static Project project;

  public static Client getCurrentClient() {
    if (currentClient != null) {
      return currentClient;
    }

    var clientInfo = new Client();

    var propertiesFileSystem = new PropertiesFileSystem();
    var properties = propertiesFileSystem.load(FileConstants.IDENTITY_FILE_PATH);
    String fullName = (String) properties.get("fullName");
    String[] fullNameSplit = fullName.split(" ");
    clientInfo.setFirstName(fullNameSplit[0]);
    clientInfo.setLastName(fullNameSplit[1]);

    String email = (String) properties.get("email");
    clientInfo.setEmail(email);

    currentClient = clientInfo;

    return clientInfo;
  }

  public static void setCurrentClient(Client client) {
    Asserts.required(client, "client cannot be null!");
    currentClient = client;
  }

  public static Project getCurrentProject() {
    if (project != null) {
      return project;
    }

    project = new Project();

    try (var scanner = new Scanner(FileConstants.SHARED_PROJECT_FILE_PATH.toFile())) {
      project.setName(scanner.nextLine());
    } catch (IOException e) {
      LOG.error("Failed to read shared project from {}", FileConstants.SHARED_PROJECT_FILE_PATH);
    }

    LOG.info("{} project read from current session.", project.getName());

    return project;
  }

  public static void setCurrentProject(Project project) {
    Asserts.required(project, "project cannot be null!");
    Session.project = project;
    LOG.info(project.getName() + " set in the current session.");
  }

  public static String getServerIpAddress() {
    if (!StringUtils.isNullOrEmpty(serverIpAddress)) {
      return serverIpAddress;
    }

    return null;
  }

  public static void setServerIpAddress(String ipAddress) {
    if (StringUtils.isNullOrEmpty(ipAddress)) {
      throw new IllegalArgumentException("ipAddress must not be null or empty!");
    }

    serverIpAddress = ipAddress;
  }

  private Session() {
  }
}
