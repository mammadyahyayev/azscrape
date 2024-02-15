package az.caspian.core.messaging;

import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

/**
 * The Client can manage projects of his own.
 * It is responsible to collect and send data between other clients.
 */
public final class Client implements Serializable {
  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private String ipAddress;
  private Map<String, String> computerDetails;
  private ClientType clientType = ClientType.WORKER;

  public Client() {
  }

  public Client(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  //region Helper Methods

  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  //endregion

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

  //region toString()
  @Override
  public String toString() {
    return new StringJoiner(", ", Client.class.getSimpleName() + "[", "]")
      .add("firstName='" + firstName + "'")
      .add("lastName='" + lastName + "'")
      .add("email='" + email + "'")
      .add("clientType=" + clientType)
      .toString();
  }
  //endregion
}
