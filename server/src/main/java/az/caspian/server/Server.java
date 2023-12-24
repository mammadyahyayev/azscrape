package az.caspian.server;

import az.caspian.core.messaging.ShortMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  private static final List<Socket> CLIENT_SOCKETS = new ArrayList<>();

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    System.out.println("Server is up and running...");
    int port = 9090;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket clientSocket = serverSocket.accept();
        if (clientSocket.isConnected()) {
          CLIENT_SOCKETS.add(clientSocket);
        }

        var inputStream = new ObjectInputStream(clientSocket.getInputStream());
        ShortMessage shortMessage = (ShortMessage) inputStream.readObject();
        System.out.println(shortMessage);
      }
    }
  }
}
