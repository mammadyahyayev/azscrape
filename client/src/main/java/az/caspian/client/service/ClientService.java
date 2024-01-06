package az.caspian.client.service;

import az.caspian.client.ClientConnection;
import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ClientService {
  private static final Logger LOG = LogManager.getLogger(ClientService.class);

  private final ClientConnection connection;

  public ClientService(ClientConnection connection) {
    this.connection = connection;
  }

  public void joinToProject(final String projectId, ClientInfo clientInfo) {
    if (StringUtils.isNullOrEmpty(projectId)) {
      throw new IllegalArgumentException("projectId cannot be null or empty!");
    }

    if (clientInfo == null) {
      throw new IllegalArgumentException("ClientInfo cannot be null!");
    }

    try {
      connection.joinToProject(projectId, clientInfo);
    } catch (IOException | InterruptedException e) {
      LOG.error("{} is failed to join to project: '{}'", clientInfo.getFullName(), projectId);
    }
  }


}
