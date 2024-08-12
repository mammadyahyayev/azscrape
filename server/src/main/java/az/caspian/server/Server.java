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

  private final ServerMessageListener serverMessageListener;

  public Server(ClientService clientService) {
    this.serverMessageListener = new DefaultServerMessageListener(clientService);
  }

  public Server(ServerMessageListener serverMessageListener) {
    this.serverMessageListener = serverMessageListener;
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
      Object message = inputStream.readObject();
      LOG.debug("Message {} sent by client", message);
      if (message instanceof ShortMessage shortMessage) {
        serverMessageListener.onMessageReceived(shortMessage);
        LOG.debug("Message {} sent to listener", shortMessage);
      }
    } catch (Exception e) {
      LOG.error("Failed to read data from client connection!");
      LOG.error("Exception: ", e);
    }
  }
}
