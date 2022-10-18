package az.my.datareport.parser;

public class UnsupportedFileFormatException extends RuntimeException {
    public UnsupportedFileFormatException() {
        super("Unsupported file!");
    }

    public UnsupportedFileFormatException(String message) {
        super(message);
    }
}
