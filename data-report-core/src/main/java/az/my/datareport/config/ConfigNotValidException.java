package az.my.datareport.config;

/**
 * Throws when user give wrong configurations
 */
public class ConfigNotValidException extends RuntimeException {
    public ConfigNotValidException() {
        super();
    }

    public ConfigNotValidException(String message) {
        super(message);
    }
}
