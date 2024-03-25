package az.caspian.core.task;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class TaskPool {
  private final BlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
  private final BlockingQueue<Task> cancelledTasks = new LinkedBlockingQueue<>();

  private TaskPool() {
  }

  public void addTasks(Collection<Task> tasks) {

  }

  public void addCancelledTask(Task cancelledTask) {

  }
}
