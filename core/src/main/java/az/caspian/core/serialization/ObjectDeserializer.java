package az.caspian.core.serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("unused")
public final class ObjectDeserializer {
  private static final Logger LOG = LogManager.getLogger(ObjectDeserializer.class);

  private ObjectDeserializer() {

  }

  public static Object deserialize(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      throw new IllegalArgumentException("Cannot deserialize from null or empty bytes!");
    }

    try (var byteInputStream = new ByteArrayInputStream(bytes);
         var objectInputStream = new ObjectInputStream(byteInputStream)) {
      return objectInputStream.readObject();
    } catch (IOException | ClassNotFoundException ex) {
      LOG.error("Failed to deserialize data, {}", ex.getMessage());
      return null;
    }
  }
}
