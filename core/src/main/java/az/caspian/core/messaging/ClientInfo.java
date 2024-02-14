package az.caspian.core.messaging;

import java.io.Serializable;
import java.util.Map;

public final class ClientInfo implements Serializable {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String ipAddress;
  private Map<String, String> computerDetails;
  private ClientType clientType = ClientType.WORKER;

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
