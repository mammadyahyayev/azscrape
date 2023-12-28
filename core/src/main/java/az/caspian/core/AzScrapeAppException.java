package az.caspian.core;

/**
 * Base exception class that used to wrap the other exception
 * classes.
 */
public class AzScrapeAppException extends RuntimeException {
  public AzScrapeAppException() {
    super();
  }

  public AzScrapeAppException(String message) {
    super(message);
  }

  public AzScrapeAppException(String message, Throwable cause) {
    super(message, cause);
  }

  public AzScrapeAppException(Throwable cause) {
    super(cause);
  }
}
