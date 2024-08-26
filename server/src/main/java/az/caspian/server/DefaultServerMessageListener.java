package az.caspian.server;

import az.caspian.core.messaging.ShortMessage;
import az.caspian.core.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultServerMessageListener implements ServerMessageListener {
  private static final Logger LOG = LogManager.getLogger(DefaultServerMessageListener.class);

  private final ClientService clientService;

  public DefaultServerMessageListener(ClientService clientService) {
    this.clientService = clientService;
  }

  @Override
  public void onMessageReceived(ShortMessage message) {
    LOG.debug("Received message {} from: {}", message, message.getSenderIpAddress());
    clientService.handleShortMessage(message);
  }
}
