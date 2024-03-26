package az.caspian.server;

import az.caspian.core.messaging.ShortMessage;
import az.caspian.core.service.ClientService;
import az.caspian.core.service.ProjectService;
import az.caspian.core.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server {
  private static final Logger LOG = LogManager.getLogger(Server.class);

  public static final int PORT = 9090;

  private final ClientService clientService;

  public Server(ClientService clientService) {
    this.clientService = clientService;
  }

  public static void main(String[] args) throws IOException {
    var server = new Server(new ClientService(new ProjectService()));

    LOG.debug("Server is up and running on port " + PORT);

    server.parseArguments(args);

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        if (!clientSocket.isConnected()) {
          continue;
        }

        server.handleClientConnection(clientSocket);
      }
    }
  }

  private void parseArguments(String[] args) {
    var index = 0;
    while (index < args.length) {
      if (args[index].equals("-p")) {
        String projectName = args[index + 1];
        LOG.debug("""
          The project '{}' is shared ({}) publicly and it is available to other clients.\s
          """, projectName, DateUtils.ofDefaultFormat(LocalDateTime.now()));
        index++;
        continue;
      }

      index++;
    }
  }

  private void handleClientConnection(Socket clientSocket) {
    try (var inputStream = new ObjectInputStream(clientSocket.getInputStream());) {
      var shortMessage = (ShortMessage) inputStream.readObject();
      LOG.debug("Message {} sent by client", shortMessage);
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
