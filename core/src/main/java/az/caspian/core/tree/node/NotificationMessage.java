package az.caspian.core.tree.node;

public record NotificationMessage(String title,
                                  String message,
                                  String description,
                                  NotificationType notificationType,
                                  boolean requiresInput) {

  public NotificationMessage(String title,
                             String message,
                             String description,
                             NotificationType notificationType) {
    this(title, message, description, notificationType, false);
  }

  @Override
  public String toString() {
    return """
      NotificationMessage [
        title: %s
        message: %s,
        description: %s,
        notificationType: %s
      ]
      """.formatted(title, message, description, notificationType);
  }
}
