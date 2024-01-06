package az.caspian.server;

import az.caspian.core.messaging.JoinToProjectMessage;
import az.caspian.core.messaging.ShortMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  private static final Logger LOG = LogManager.getLogger(Server.class);

  private static final List<Socket> CLIENT_SOCKETS = new ArrayList<>();

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    LOG.debug("Server is up and running...");
    int port = 9090;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        if (clientSocket.isConnected()) {
          CLIENT_SOCKETS.add(clientSocket);
        }

        try (var inputStream = new ObjectInputStream(clientSocket.getInputStream());) {
          var shortMessage = (ShortMessage) inputStream.readObject();
          parseMessage(shortMessage);
        }
      }
    }
  }

  public static void parseMessage(ShortMessage message) {
    if (message instanceof JoinToProjectMessage joinToProjectMessage) {
      LOG.debug("{} wants to join to {} project",
        joinToProjectMessage.clientInfo().getFullName(), joinToProjectMessage.projectId());
    }
  }
}
