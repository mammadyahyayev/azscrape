package az.caspian.core.messaging;

public record JoinToProjectMessage(Client client) implements ShortMessage {
  @Override
  public String getSenderIpAddress() {
    return client().getIpAddress();
  }
}
