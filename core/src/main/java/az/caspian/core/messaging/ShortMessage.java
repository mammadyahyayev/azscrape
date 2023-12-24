package az.caspian.core.messaging;

import java.io.Serializable;

public record ShortMessage(String projectId, ClientInfo clientInfo) implements Serializable {
}
