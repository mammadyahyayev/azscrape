package az.caspian.core.task;

import az.caspian.core.messaging.Client;
import az.caspian.core.template.ScrapeTemplate;

public class Task<T extends ScrapeTemplate> {
  private final String id;
  private final String name;
  private final T template;
  private final Client assignee;
  private String description;

  public Task(String name, T template, Client assignee) {
    this.id = ""; //TODO: Create random id for task
    this.name = name;
    this.template = template;
    this.assignee = assignee;
  }

  public Task(String id, String name, T template, Client assignee) {
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

  public T getTemplate() {
    return template;
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
