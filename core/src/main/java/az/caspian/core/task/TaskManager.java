package az.caspian.core.task;

import az.caspian.core.remote.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class TaskManager {
  private static final Logger LOG = LogManager.getLogger(TaskManager.class);

  public void sendTasks(Collection<Task> tasks) {
    int sharedTasks = 0;
    for (Task task : tasks) {
      boolean isShared = ClientConnection.sendTaskToClient(task);
      if (isShared) sharedTasks++;
    }

    LOG.info(sharedTasks + " out of " + tasks.size() + " tasks shared with clients!");
  }

}
