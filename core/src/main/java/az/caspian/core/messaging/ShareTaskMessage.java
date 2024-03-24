package az.caspian.core.messaging;

import az.caspian.core.task.Task;

public record ShareTaskMessage(Task task) implements ShortMessage {
}
