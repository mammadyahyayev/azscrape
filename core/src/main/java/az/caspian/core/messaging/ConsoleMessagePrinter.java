package az.caspian.core.messaging;

import az.caspian.core.tree.node.NotificationMessage;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ConsoleMessagePrinter {

  private static final ConcurrentLinkedQueue<NotificationMessage> CONSOLE_MESSAGES = new ConcurrentLinkedQueue<>();
  private final AtomicBoolean isRunning = new AtomicBoolean(true);
  private final Thread messageProcessingThread;
  private final Scanner scanner = new Scanner(System.in);

  public ConsoleMessagePrinter() {
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
        processMessage(message);
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        isRunning.set(false);
      }
    }
  }

  private void processMessage(NotificationMessage message) {
    if (!message.requiresInput()) {
      System.out.println(message);
      return;
    }

    System.out.println(message);
    System.out.println("Please, type one of these for next (1. Continue (c),  2. Stop (s)): ");
    handleInput();
  }

  private void handleInput() {
    String action = scanner.nextLine();
    boolean isSelected = false;

    while (!isSelected) {
      switch (action) {
        case "c":
          isSelected = true;
          break;
        case "s":
          isSelected = true;
          break;
        default:
          System.err.println("Invalid action: " + action);
      }
    }
  }
}
