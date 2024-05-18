package az.caspian.core.tree.node;

public class InterventionNodeNotificationListener implements Listener {

  @Override
  public void listen(NotificationMessage notificationMessage) {
    System.out.println(notificationMessage);

    // TODO: create a class ConsoleMessageDispatcher, it will dispatch all the messages into ConcurrentQueue,
    //  so messages will be read from them and send it to the console when it is okay.
  }
}
