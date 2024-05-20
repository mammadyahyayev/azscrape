package az.caspian.core.messaging;

import az.caspian.core.tree.node.NotificationMessage;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ConsoleMessageDispatcher {

  private static final ConcurrentLinkedQueue<NotificationMessage> CONSOLE_MESSAGES = new ConcurrentLinkedQueue<>();
  private final AtomicBoolean isRunning = new AtomicBoolean(true);
  private final Thread messageProcessingThread;

  public ConsoleMessageDispatcher() {
    messageProcessingThread = new Thread(this::processMessages);
    messageProcessingThread.setName("Console Message Processing");
    messageProcessingThread.setDaemon(true);
  }

  public void start() {
    if (!messageProcessingThread.isAlive()) {
      messageProcessingThread.start();
    }
  }

  public void dispatchConsoleMessage(final NotificationMessage message) {
    Objects.requireNonNull(message, "NotificationMessage cannot be null");

    CONSOLE_MESSAGES.add(message);
  }

  public void processMessages() {
    while (isRunning.get()) {
      while (!CONSOLE_MESSAGES.isEmpty()) {
        NotificationMessage message = CONSOLE_MESSAGES.poll();
        System.out.println(message);
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        isRunning.set(false);
      }
    }
  }
}
