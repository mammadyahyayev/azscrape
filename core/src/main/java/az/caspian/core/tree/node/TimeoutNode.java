package az.caspian.core.tree.node;

import java.util.concurrent.TimeUnit;

public class TimeoutNode extends Node {
  private final long timeout;

  public TimeoutNode(long timeoutInMs) {
    this.timeout = timeoutInMs;
  }

  public TimeoutNode(long timeout, TimeUnit timeUnit) {
    this.timeout = timeUnit.toMillis(timeout);
  }

  public long getTimeout() {
    return timeout;
  }

  public long getTimeoutInSeconds() {
    return TimeUnit.MILLISECONDS.toSeconds(timeout);
  }
}
