package az.caspian.core.task;

import az.caspian.core.messaging.Client;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.utils.RandomUtils;

public class Task {
  private final String id;
  private final String name;
  private final ScrapeTemplate template;
  private Client assignee;
  private String description;

  public Task(String name, ScrapeTemplate template, Client assignee) {
    this.id = RandomUtils.generateRandomKey();
    this.name = name;
    this.template = template;
    this.assignee = assignee;
  }

  public Task(String id, String name, ScrapeTemplate template, Client assignee) {
    this.id = id;
    this.name = name;
    this.template = template;
    this.assignee = assignee;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ScrapeTemplate getTemplate() {
    return template;
  }

  public void setAssignee(Client assignee) {
    this.assignee = assignee;
  }

  public Client getAssignee() {
    return assignee;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
