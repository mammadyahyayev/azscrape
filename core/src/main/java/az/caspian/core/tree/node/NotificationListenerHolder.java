package az.caspian.core.tree.node;

import az.caspian.core.messaging.ConsoleMessageDispatcher;

import java.util.HashMap;
import java.util.Map;

public class NotificationListenerHolder {

  private static final Map<NotificationMethod, Listener> notificationListeners = initListeners();

  public Listener getNotificationListener(NotificationMethod method) {
    return notificationListeners.get(method);
  }

  private static Map<NotificationMethod, Listener> initListeners() {
    var consoleMessageDispatcher = new ConsoleMessageDispatcher();

    var notificationListeners = new HashMap<NotificationMethod, Listener>();
    notificationListeners.putIfAbsent(
      NotificationMethod.CONSOLE,
      new InterventionNodeNotificationListener(consoleMessageDispatcher)
    );

    return notificationListeners;
  }
}
