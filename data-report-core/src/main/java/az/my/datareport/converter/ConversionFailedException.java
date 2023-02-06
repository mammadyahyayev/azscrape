package az.my.datareport.converter;

class ConversionFailedException extends RuntimeException {
    public ConversionFailedException() {
        super();
    }

    public ConversionFailedException(String message) {
        super(message);
    }

    public ConversionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
