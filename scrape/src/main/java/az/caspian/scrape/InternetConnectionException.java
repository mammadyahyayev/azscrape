package az.caspian.scrape;

/**
 * Throws, when user doesn't have Internet Connection
 */
public class InternetConnectionException extends RuntimeException {
    public InternetConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
