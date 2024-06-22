package az.caspian.core.tree.node;

import az.caspian.core.messaging.ConsoleMessagePrinter;

public class InterventionNodeNotificationListener implements Listener {

  private final ConsoleMessagePrinter consoleMessagePrinter;

  public InterventionNodeNotificationListener(ConsoleMessagePrinter consoleMessagePrinter) {
    this.consoleMessagePrinter = consoleMessagePrinter;
  }

  @Override
  public void listen(NotificationMessage<?> notificationMessage) {
    consoleMessagePrinter.dispatchConsoleMessage(notificationMessage);
    consoleMessagePrinter.start();
  }
}
