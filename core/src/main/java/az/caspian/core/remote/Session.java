package az.caspian.core.remote;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.PropertiesFileSystem;
import az.caspian.core.utils.StringUtils;

public final class Session {
  private static String serverIpAddress;
  private static ClientInfo currentClient;
  private static Project project;

  public static ClientInfo getCurrentClient() {
    if (currentClient != null) {
      return currentClient;
    }

    var clientInfo = new ClientInfo();

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

  public static Project getCurrentProject() {
    if (project != null) {
      return project;
    }

    //TODO: find a way to set active project in here, it will be not null
    // for client who shares it, but it will be null for other clients.

    //TODO: IDEA: Create a new text file called shared project and add
    // project name inside it. whenever user rerun the program again and wants to share project
    // suggest the project inside that file.

    return project;
  }

  public static void setCurrentProject(Project project) {
    Asserts.required(project, "project cannot be null!");
    Session.project = project;
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
