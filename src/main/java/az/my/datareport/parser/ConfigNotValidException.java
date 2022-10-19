package az.my.datareport.parser;

public class ConfigNotValidException extends RuntimeException {
    public ConfigNotValidException() {
        super();
    }

    public ConfigNotValidException(String message) {
        super(message);
    }
}
