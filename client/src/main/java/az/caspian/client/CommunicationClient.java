package az.caspian.client;

import az.caspian.core.messaging.ClientInfo;
import az.caspian.core.messaging.ShortMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CommunicationClient {
  public static void main(String[] args) {
    try {
      joinToProject("12345", new ClientInfo());
      Thread.currentThread().join();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void joinToProject(final String projectId, ClientInfo client) throws IOException, InterruptedException {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress("localhost", 9090));
      System.out.println("Client made connection to server...");

      var outputStream = new ObjectOutputStream(socket.getOutputStream());
      outputStream.writeObject(new ShortMessage(projectId, client));
      outputStream.writeObject("Test");
      outputStream.close();
      Thread.currentThread().join();
    }
  }
}
