package az.caspian.client;

import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.messaging.JoinToProjectMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientConnection {
  private static final Logger LOG = LogManager.getLogger(ClientConnection.class);

  public void joinToProject(final String projectId, ClientInfo client) throws IOException, InterruptedException {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress("localhost", 9090));
      LOG.debug("Client made connection to server...");

      var outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeObject(new JoinToProjectMessage(projectId, client));
      outputStream.close();
    }
  }
}
