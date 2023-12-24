package az.caspian.client;

import az.caspian.core.messaging.ShortMessage;

public final class ByteArrayUtil {
  private ByteArrayUtil() {
    throw new IllegalStateException("Can't create instance of this class!");
  }

  public static byte[] serialize(ShortMessage message) {
    return new byte[0];
  }
}
