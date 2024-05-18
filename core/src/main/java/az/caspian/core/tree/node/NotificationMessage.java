package az.caspian.core.tree.node;

public record NotificationMessage(String title,
                                  String message,
                                  String description,
                                  NotificationType notificationType) {
}
