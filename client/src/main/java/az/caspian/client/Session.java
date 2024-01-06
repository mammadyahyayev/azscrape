package az.caspian.client;

import az.caspian.core.messaging.ClientInfo;

public final class Session {
  private Session() {
  }

  public static ClientInfo getCurrentClient() {
    return new ClientInfo();
  }
}
