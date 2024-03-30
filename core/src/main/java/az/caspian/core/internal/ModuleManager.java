package az.caspian.core.internal;

import az.caspian.core.constant.AzScrapeVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Map;

public final class ModuleManager {
  private static final Logger LOG = LogManager.getLogger(ModuleManager.class);

  public static boolean runServerModule(Map<String, String> cliArguments) {
    StringBuilder command = getRunServerCommand(cliArguments);

    try {
      Process process = Runtime.getRuntime().exec(command.toString());
      var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String output;
      while ((output = reader.readLine()) != null) {
        LOG.debug("Output: {}", output);
      }
    } catch (IOException e) {
      LOG.error("Failed to run server module!");
      return false;
    }

    return true;
  }

  @NotNull
  private static StringBuilder getRunServerCommand(Map<String, String> cliArguments) {
    var projectDir = System.getProperty("user.dir");
    var projectVersion = AzScrapeVersion.getVersion();
    var jarFileName = "lib/server-%s-jar-with-dependencies.jar".formatted(projectVersion);
    var jarFilePath = Path.of(projectDir, jarFileName);
    var mainClass = "az.caspian.server.Server";

    var command = new StringBuilder("java -cp %s %s".formatted(jarFilePath, mainClass));

    for (var cliArgument : cliArguments.entrySet()) {
      String flag = cliArgument.getKey();
      String value = cliArgument.getValue();

      if (flag == null) continue;
      if (value == null) value = "";

      command.append(" %s %s".formatted(flag, value));
    }

    return command;
  }

  private ModuleManager() {
    throw new IllegalStateException("Can't instantiate!");
  }
}
