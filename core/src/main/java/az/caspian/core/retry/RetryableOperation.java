package az.caspian.core.retry;

public interface RetryableOperation {

  void perform();

  static void retry(RetryableOperation retryableOperation, int maxAttempts) {
    if (maxAttempts <= 0) {
      throw new IllegalArgumentException("Max attempts must be greater than zero");
    }

    int attempts = 0;
    while (attempts <= maxAttempts) {
      try {
        retryableOperation.perform();
        break;
      } catch (Exception e) {
        attempts++;
      }
    }

    if (attempts > maxAttempts) {
      throw new RetryExhaustedException(maxAttempts);
    }
  }
}
