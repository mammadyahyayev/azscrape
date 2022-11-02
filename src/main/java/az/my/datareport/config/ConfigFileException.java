package az.my.datareport.config;

/**
 * Base class for wrapping Config File related exceptions
 */
public class ConfigFileException extends RuntimeException {
    public ConfigFileException() {
        super();
    }

    public ConfigFileException(String message) {
        super(message);
    }

    public ConfigFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigFileException(Throwable cause) {
        super(cause);
    }
}
