package az.caspian.core.remote;

import az.caspian.core.messaging.Client;
import az.caspian.core.utils.Asserts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project {
  private String name;
  private LocalDateTime createdAt;
  private Client createdBy;
  private List<Client> attendants;

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

  public Client getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Client createdBy) {
    this.createdBy = createdBy;
  }

  public List<Client> getAttendants() {
    return attendants;
  }

  public void setAttendants(List<Client> attendants) {
    this.attendants = attendants;
  }

  public void addAttendant(Client client) {
    Asserts.required(client, "client is required!");

    this.attendants.add(client);
  }
}
