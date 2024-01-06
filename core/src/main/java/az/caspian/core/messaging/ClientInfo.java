package az.caspian.core.messaging;

import az.caspian.core.constant.FileConstants;
import az.caspian.core.utils.PropertiesFileSystem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

enum ClientType {
  MASTER, SLAVE
}

public final class ClientInfo implements Serializable {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String ipAddress;
  private Map<String, String> computerDetails = new HashMap<>();
  private ClientType clientType = ClientType.SLAVE;

  {
    init();
  }

  public void init() {
    var propertiesFileSystem = new PropertiesFileSystem();
    var properties = propertiesFileSystem.load(FileConstants.IDENTITY_FILE_PATH);

    this.firstName = properties.getProperty("firstName");
    this.lastName = properties.getProperty("lastName");
    this.email = properties.getProperty("email");

    computerDetails.put("code", properties.getProperty("computer.code"));
    computerDetails.put("brand", properties.getProperty("computer.brand"));
    computerDetails.put("model", properties.getProperty("computer.model"));
    computerDetails.put("ram", properties.getProperty("computer.ram"));
  }

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  //#region Getters & Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public Map<String, String> getComputerDetails() {
    return computerDetails;
  }

  public void setComputerDetails(Map<String, String> computerDetails) {
    this.computerDetails = computerDetails;
  }

  public ClientType getClientType() {
    return clientType;
  }

  public void setClientType(ClientType clientType) {
    this.clientType = clientType;
  }
  //#endregion
}
