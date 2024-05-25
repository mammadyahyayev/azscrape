package az.caspian.core.tree.node;

import az.caspian.core.messaging.ConsoleMessagePrinter;

import java.util.EnumMap;
import java.util.Map;

public class NotificationListenerHolder {

  private static final Map<NotificationMethod, Listener> notificationListeners = initListeners();

  public Listener getNotificationListener(NotificationMethod method) {
    return notificationListeners.get(method);
  }

  private static Map<NotificationMethod, Listener> initListeners() {
    var consoleMessageDispatcher = new ConsoleMessagePrinter();

    Map<NotificationMethod, Listener> notificationListeners = new EnumMap<>(NotificationMethod.class);
    notificationListeners.putIfAbsent(
      NotificationMethod.CONSOLE,
      new InterventionNodeNotificationListener(consoleMessageDispatcher)
    );

    return notificationListeners;
  }
}
