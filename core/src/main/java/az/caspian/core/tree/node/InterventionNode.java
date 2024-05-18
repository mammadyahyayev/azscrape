package az.caspian.core.tree.node;

import java.util.concurrent.TimeUnit;

/**
 * When {@link InterventionNode} is executed, it will wait manual interaction
 * of human until the given timeout expired.
 */
public class InterventionNode extends Node {

  private static final int DEFAULT_EXPIRED_TIME_IN_SECONDS = 20;
  private static final String DEFAULT_INTERVENTION_MESSAGE = "Human Intervention Required!";

  /**
   * Helper message for clients to understand what they have to do when intervention
   * needed.
   */
  private final long expiredAfterInSec;
  private final String message;
  private final String description;
  private final NotificationMethod notificationMethod;
  private Status status;

  public InterventionNode() {
    this(DEFAULT_EXPIRED_TIME_IN_SECONDS, TimeUnit.SECONDS, NotificationMethod.CONSOLE_WITH_SOUND);
  }

  public InterventionNode(long value, TimeUnit timeUnit, NotificationMethod notificationMethod) {
    this(value, timeUnit, DEFAULT_INTERVENTION_MESSAGE, notificationMethod);
  }

  public InterventionNode(long value, TimeUnit timeUnit, String message, NotificationMethod notificationMethod) {
    this(timeUnit.toSeconds(value), message, "", Status.INITIAL, notificationMethod);
  }

  private InterventionNode(long valueInSeconds,
                           String message,
                           String description,
                           Status status,
                           NotificationMethod notificationMethod) {
    this.expiredAfterInSec = valueInSeconds;
    this.message = message;
    this.description = description;
    this.status = status;
    this.notificationMethod = notificationMethod;
  }

  public String getDescription() {
    return description;
  }

  public String getMessage() {
    return message;
  }

  public Status getStatus() {
    return status;
  }

  public long getExpiredAfterInMs() {
    return expiredAfterInSec;
  }

  public NotificationMethod getNotificationMethod() {
    return notificationMethod;
  }

  public enum Status {
    INITIAL, WAIT, CONTINUE, STOP
  }
}
