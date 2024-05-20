package az.caspian.core.tree.node;

import az.caspian.core.messaging.ConsoleMessageDispatcher;

public class InterventionNodeNotificationListener implements Listener {

  private final ConsoleMessageDispatcher consoleMessageDispatcher;

  public InterventionNodeNotificationListener(ConsoleMessageDispatcher consoleMessageDispatcher) {
    this.consoleMessageDispatcher = consoleMessageDispatcher;
  }

  @Override
  public void listen(NotificationMessage notificationMessage) {
    consoleMessageDispatcher.dispatchConsoleMessage(notificationMessage);
    consoleMessageDispatcher.start();
  }
}
