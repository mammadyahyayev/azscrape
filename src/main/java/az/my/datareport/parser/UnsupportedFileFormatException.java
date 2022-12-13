package az.my.datareport.parser;

public class UnsupportedFileFormatException extends RuntimeException {
    public UnsupportedFileFormatException() {
        super("Unsupported file format!");
    }

    public UnsupportedFileFormatException(String message) {
        super(message);
    }

    public UnsupportedFileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
