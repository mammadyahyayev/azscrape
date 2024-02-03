package az.caspian.core.messaging;

public record JoinToProjectMessage(ClientInfo clientInfo) implements ShortMessage {
}
