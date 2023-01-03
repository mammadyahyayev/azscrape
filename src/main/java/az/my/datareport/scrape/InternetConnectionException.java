package az.my.datareport.scrape;

public class InternetConnectionException extends RuntimeException {
    public InternetConnectionException() {
        super();
    }

    public InternetConnectionException(String message) {
        super(message);
    }

    public InternetConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternetConnectionException(Throwable cause) {
        super(cause);
    }

    protected InternetConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
