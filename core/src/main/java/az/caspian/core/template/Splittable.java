package az.caspian.core.template;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;

import java.util.List;

/**
 * The interface is used by scrape templates to create different tasks that can
 * be shared between clients.
 */
public interface Splittable {
  List<Task> split(String taskName, List<Client> clients);

  List<Task> split(String taskName, List<Client> clients, SplitStrategy strategy);
}
