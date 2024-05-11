package az.caspian.core.tree.node;

import java.util.concurrent.TimeUnit;

/**
 * When {@link InterventionNode} is executed, it will wait manual interaction
 * of human forever, or it can wait until the given timeout expired.
 */
public class InterventionNode extends Node {

  /**
   * Helper message for clients to understand what they have to do when intervention
   * needed.
   */
  private String message;
  private Status status;
  private long expiredAfterInMs = 20000;

  public InterventionNode() {
  }

  public InterventionNode(long value, TimeUnit timeUnit) {
    this.expiredAfterInMs = timeUnit.toMillis(value);
  }

  public String getMessage() {
    return message;
  }

  public Status getStatus() {
    return status;
  }

  public long getExpiredAfterInMs() {
    return expiredAfterInMs;
  }

  public enum Status {
    INITIAL, WAIT, CONTINUE, STOP
  }
}
