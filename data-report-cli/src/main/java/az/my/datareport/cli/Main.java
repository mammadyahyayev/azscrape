package az.my.datareport.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        init();
        Logs logs = new Logs(System.out, System.err);
        Exit exit = new Exit();
        Cli cli = new Cli(logs, exit);
        cli.parse(args);
    }

    public static void init() {
        String userHomeDir = System.getProperty("user.home");
        String ymFolder = userHomeDir + File.separator + ".ym";
        File file = new File(ymFolder);
        if (!file.exists()) {
            try {
                Files.createDirectory(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
