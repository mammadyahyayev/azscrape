package az.caspian.core.retry;

public class RetryExhaustedException extends RuntimeException {

  public RetryExhaustedException(int maxAttempts) {
    this("Retry exhausted after " + maxAttempts + " attempts!");
  }

  public RetryExhaustedException(String message) {
    super(message);
  }
}
