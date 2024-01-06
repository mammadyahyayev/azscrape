package az.caspian.client.model;

import java.io.Serializable;

/**
 * A Client.
 */
public class Client implements Serializable {
  private String firstName;
  private String lastName;
  private String email;
  private String ipAddress;
  private ComputerDetails computerDetails;

  static {
    init();
  }

  public static void init(){

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

  public ComputerDetails getComputerDetails() {
    return computerDetails;
  }

  public void setComputerDetails(ComputerDetails computerDetails) {
    this.computerDetails = computerDetails;
  }
}
