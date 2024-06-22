package az.caspian.core.tree.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InterventionNotifier implements Notification {

  private final InterventionNode interventionNode;
  private final List<Listener> listeners;

  public InterventionNotifier(InterventionNode interventionNode) {
    this.interventionNode = interventionNode;
    this.listeners = new ArrayList<>();

    var listenerHolder = new NotificationListenerHolder();
    Listener listener = listenerHolder.getNotificationListener(interventionNode.getNotificationMethod());
    listeners.add(listener);
  }

  @Override
  public void notifyClients() {
    interventionNode.setStatus(InterventionNode.Status.WAIT);

    var message = new NotificationMessage<>(
      interventionNode,
      "Intervention Node",
      interventionNode.getMessage(),
      interventionNode.getDescription(),
      NotificationType.HUMAN_INTERACTION_NEED,
      true
    );

    listeners.forEach(listener -> listener.listen(message));
  }

  @Override
  public void addListener(Listener listener) {
    Objects.requireNonNull(listener);

    listeners.add(listener);
  }

  @Override
  public void removeListener(Listener listener) {
    Objects.requireNonNull(listener);

    listeners.remove(listener);
  }
}
