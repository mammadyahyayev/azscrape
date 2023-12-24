package az.caspian.core.messaging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

enum ClientType {
  MASTER, SLAVE
}

public final class ClientInfo implements Serializable {
  private String id;
  private String ipAddress;
  private String name;
  private Map<String, String> computerDetails = new HashMap<>();
  private ClientType clientType = ClientType.SLAVE;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
}
