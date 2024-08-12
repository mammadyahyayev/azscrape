package az.caspian.server;

import az.caspian.core.messaging.ShortMessage;

public interface ServerMessageListener {

  void onMessageReceived(ShortMessage message);
}
