package az.caspian.core.messaging;

public record JoinToProjectMessage(String projectId, ClientInfo clientInfo) implements ShortMessage {
}
