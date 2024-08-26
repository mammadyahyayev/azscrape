package az.caspian.core.messaging;

import az.caspian.core.task.Task;

public record ShareTaskMessage(Client taskSender, Task task) implements ShortMessage {
  @Override
  public String getSenderIpAddress() {
    return taskSender.getIpAddress();
  }
}
