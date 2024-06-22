package az.caspian.core.tree.node;

public record NotificationMessage<T>(
  T object,
  String title,
  String message,
  String description,
  NotificationType notificationType,
  boolean requiresInput) {

  public NotificationMessage(T object,
                             String title,
                             String message,
                             String description,
                             NotificationType notificationType) {
    this(object, title, message, description, notificationType, false);
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
