package az.caspian.core.tree.node;

public interface Notification {

  void notifyClients();

  void addListener(Listener listener);

  void removeListener(Listener listener);
}
