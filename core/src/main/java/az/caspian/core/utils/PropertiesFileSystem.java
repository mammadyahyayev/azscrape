package az.caspian.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

public class PropertiesFileSystem extends AbstractFileSystem {
    private static final Logger LOG = LogManager.getLogger(PropertiesFileSystem.class);

    private final Properties properties;

    public PropertiesFileSystem() {
        this.properties = new Properties();
    }

    public PropertiesFileSystem(String propertyFilePath) {
        Objects.requireNonNull(propertyFilePath, "Path of property file cannot be null or empty");
        this.properties = new Properties();
        load(propertyFilePath);
    }

    public Properties load(String propertiesFilePath) {
        Objects.requireNonNull(propertiesFilePath, "Path of property file cannot be null or empty");

        try {
            properties.load(new FileInputStream(propertiesFilePath));
            return properties;
        } catch (IOException e) {
            String message = MessageFormat.format("Failed to read from file {0}", propertiesFilePath);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }
    }

    public void store(String propertiesFilePath, Properties properties) {
        try (OutputStream outputStream = new FileOutputStream(propertiesFilePath)) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            String message = MessageFormat.format("Failed to write into file {0}", propertiesFilePath);
            LOG.error(message);
            throw new RuntimeException(message, e);
        }
    }
}