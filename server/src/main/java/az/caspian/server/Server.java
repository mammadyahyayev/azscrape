package az.caspian.server;

import az.caspian.core.messaging.ShortMessage;
import az.caspian.core.service.ClientService;
import az.caspian.core.service.ProjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private static final Logger LOG = LogManager.getLogger(Server.class);

  private final ClientService clientService;

  public Server(ClientService clientService) {
    this.clientService = clientService;
  }

  public static void main(String[] args) throws IOException {
    var server = new Server(new ClientService(new ProjectService()));

    LOG.debug("Server is up and running...");
    int port = 9090;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        if (!clientSocket.isConnected()) {
          continue;
        }

        server.handleClientConnection(clientSocket);
      }
    }
  }

  private void handleClientConnection(Socket clientSocket) {
    try (var inputStream = new ObjectInputStream(clientSocket.getInputStream());) {
      var shortMessage = (ShortMessage) inputStream.readObject();
      parseMessage(shortMessage);
    } catch (Exception e) {
      LOG.error("Failed to read data from client connection!");
    }
  }

  private void parseMessage(ShortMessage message) {
    clientService.handleShortMessage(message);
  }

  //TODO: projectOwnerIpAddress is required to send data to server, in our case server will
  // be projectOwner.
}
