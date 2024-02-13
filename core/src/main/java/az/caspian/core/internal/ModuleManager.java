package az.caspian.core.internal;

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

  public static void runServerModule(Map<String, String> cliArguments) {
    StringBuilder command = getRunServerCommand(cliArguments);

    try {
      Process process = Runtime.getRuntime().exec(command.toString());
      var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String output;
      while ((output = reader.readLine()) != null) {
        System.out.println(output);
      }
    } catch (
      IOException e) {
      LOG.error("Failed to run server module!");
    }
  }

  @NotNull
  private static StringBuilder getRunServerCommand(Map<String, String> cliArguments) {
    var projectDir = System.getProperty("user.dir");
    Path jarFilePath = Path.of(projectDir, "lib/server-3.0.0-SNAPSHOT-jar-with-dependencies.jar"); //TODO: Get jar version dynamically
    String mainClass = "az.caspian.server.Server";

    StringBuilder command = new StringBuilder("java -cp %s %s".formatted(jarFilePath, mainClass));

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
