package az.caspian.core.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

public class PropertiesFileSystem extends AbstractFileSystem {
  private static final Logger LOG = LogManager.getLogger(PropertiesFileSystem.class);

  private final Properties properties;

  public PropertiesFileSystem() {
    this.properties = new Properties();
  }

  public PropertiesFileSystem(Path filePath) {
    Objects.requireNonNull(filePath, "Path of property file cannot be null or empty");
    this.properties = new Properties();
    load(filePath);
  }

  public Properties load(Path filePath) {
    Objects.requireNonNull(filePath, "Path of property file cannot be null or empty");

    try {
      properties.load(new FileInputStream(filePath.toString()));
      return properties;
    } catch (IOException e) {
      String message = MessageFormat.format("Failed to read from file {0}", filePath);
      LOG.error(message);
      throw new RuntimeException(message, e);
    }
  }

  public Properties load(InputStream inputStream) {
    Objects.requireNonNull(inputStream, "inputStream cannot be null!");

    try {
      properties.load(inputStream);
      return properties;
    } catch (IOException e) {
      String message = "Failed to read from inputStream";
      LOG.error(message);
      throw new RuntimeException(message, e);
    }
  }

  public void store(Path path, Properties properties) {
    try (OutputStream outputStream = new FileOutputStream(path.toString())) {
      properties.store(outputStream, null);
      LOG.debug("Properties is stored on " + path);
    } catch (IOException e) {
      String message = MessageFormat.format("Failed to write into file {0}", path);
      LOG.error(message);
      throw new RuntimeException(message, e);
    }
  }
}
