package az.caspian.core.serialization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public final class ObjectSerializer {
  private static final Logger LOG = LogManager.getLogger(ObjectSerializer.class);

  public static byte[] serialize(Object obj) {
    Objects.requireNonNull(obj, "Cannot serialize null object!");

    try (var bos = new ByteArrayOutputStream(); var oos = new ObjectOutputStream(bos)) {
      oos.writeObject(obj);
      return bos.toByteArray();
    } catch (IOException e) {
      LOG.error("Failed to serialize data!", e);
      return null;
    }
  }
}
