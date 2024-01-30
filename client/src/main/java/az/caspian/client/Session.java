package az.caspian.client;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.PropertiesFileSystem;

public final class Session {
  private static ClientInfo currentClient;

  private Session() {
  }

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
}
