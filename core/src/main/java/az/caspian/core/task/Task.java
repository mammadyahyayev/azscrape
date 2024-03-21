package az.caspian.core.task;

import az.caspian.core.messaging.Client;
import az.caspian.core.template.ScrapeTemplate;

public class Task<T extends ScrapeTemplate> {
  private String id;
  private String name;
  private T template;
  private Client assignee;
  private String description;

  public Task(String name, T template, Client assignee) {
    this.id = "";
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

  public T getTemplate() {
    return template;
  }

  public Client getAssignee() {
    return assignee;
  }

  public String getDescription() {
    return description;
  }
}
