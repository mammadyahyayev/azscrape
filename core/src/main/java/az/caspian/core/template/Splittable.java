package az.caspian.core.template;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;

import java.util.List;

/**
 * The interface is used by scrape templates to create different tasks that can
 * be shared between clients.
 */
public interface Splittable {

  /**
   * Splits template between clients based {@link SplitStrategy#EQUAL} strategy.
   *
   * @param taskName a task name that will be assigned to each created task
   * @param clients  list of {@link Client}s who will be assigned to tasks
   * @return list of {@link Task}
   * @see #split(String, List, SplitStrategy)
   */
  List<Task> split(String taskName, List<Client> clients);

  /**
   * Splits template between clients based {@link SplitStrategy#EQUAL} strategy.
   *
   * @param taskName a task name that will be assigned to each created task
   * @param clients  list of {@link Client}s who will be assigned to tasks
   * @param strategy a {@link SplitStrategy}
   * @return list of {@link Task}
   */
  List<Task> split(String taskName, List<Client> clients, SplitStrategy strategy);

  /**
   * Splits templates into tasks for only one computer to execute them in multiple threads.
   * Tasks will be divided based on {@link SplitStrategy#EQUAL} and <i>number of available cores</i>
   * in the machine.
   *
   * @param taskName a task name that will be assigned to each created task
   * @return list of {@link Task}
   */
  List<Task> splitInternally(String taskName);

  /**
   * Splits templates into tasks for only one computer to execute them in multiple threads.
   * Tasks will be divided based on {@link SplitStrategy#EQUAL}.
   *
   * <p>
   * <b>NOTE:</b> taskCounts parameter is given and supposed to use testing purposes. It is
   * recommended to give fair number (number of available cores in the machine) for tasks because
   * each task will be executed from one thread at a time. If you want more performant approach,
   * call {@link #splitInternally(String)} method to handle the process behind.
   * </p>
   *
   * @param taskName   a task name that will be assigned to each created task
   * @param taskCounts number of counts of tasks after division of template
   * @return list of {@link Task}
   */
  List<Task> splitInternally(String taskName, int taskCounts);

}
