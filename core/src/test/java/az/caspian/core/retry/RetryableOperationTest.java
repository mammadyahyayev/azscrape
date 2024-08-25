package az.caspian.core.retry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetryableOperationTest {

  @Test
  void throwRetryExhaustedException_after3Attempts() {
    assertThrows(RetryExhaustedException.class, () -> RetryableOperation.retry(this::throwExceptionIntentionally, 3));
  }

  public void throwExceptionIntentionally() {
    throw new RuntimeException();
  }

}