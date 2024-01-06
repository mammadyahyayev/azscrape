package az.caspian.client;

import az.caspian.client.service.ClientService;
import az.caspian.client.ui.JoinToProjectFrame;
import az.caspian.core.AzScrapeAppException;
import az.caspian.core.constant.FileConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

public class Main {
  private static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    LOG.debug("AZScrape Client is about to start...");

    try {
      if (!Files.exists(FileConstants.APP_PATH)) {
        Files.createDirectory(FileConstants.APP_PATH);
        LOG.debug("Main app folder is created...");
      }
    } catch (IOException e) {
      LOG.fatal("Failed to create main application folder!");
      throw new AzScrapeAppException("Failed to create main app folder", e);
    }

    var clientService = new ClientService(new ClientConnection());
    new JoinToProjectFrame(clientService);
  }
}
