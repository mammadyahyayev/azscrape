package az.caspian.core.remote;

import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.Asserts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
  private String name;
  private LocalDateTime createdAt;
  private ClientInfo createdBy;
  private List<ClientInfo> attendants;

  public Project() {
    attendants = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ClientInfo getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(ClientInfo createdBy) {
    this.createdBy = createdBy;
  }

  public List<ClientInfo> getAttendants() {
    return attendants;
  }

  public void setAttendants(List<ClientInfo> attendants) {
    this.attendants = attendants;
  }

  public void addAttendant(ClientInfo clientInfo) {
    Asserts.required(clientInfo, "clientInfo is required!");

    this.attendants.add(clientInfo);
  }
}
