package az.caspian.core.task;

import az.caspian.core.remote.ClientConnection;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.TemplateExecutor;
import az.caspian.core.tree.DataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class TaskManager {
  private static final Logger LOG = LogManager.getLogger(TaskManager.class);

  private final TemplateExecutor templateExecutor;

  public TaskManager(TemplateExecutor templateExecutor) {
    this.templateExecutor = templateExecutor;
  }

  public void sendTasks(Collection<Task> tasks) {
    int sharedTasks = 0;
    for (Task task : tasks) {
      boolean isShared = ClientConnection.sendTaskToClient(task);
      if (isShared) sharedTasks++;
    }

    LOG.info(sharedTasks + " out of " + tasks.size() + " tasks shared with clients!");
  }

  public void executeTask(Task task) {
    ScrapeTemplate template = task.getTemplate();
    DataTable dataTable = templateExecutor.executeTemplate(template);
    System.out.println(dataTable);
  }

}
