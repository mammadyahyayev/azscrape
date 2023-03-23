package az.my.datareport.cli;

import java.util.Arrays;
import java.util.Scanner;

public class Cli {
    private final Logs LOG;
    private final Exit exit;
    private final ConsoleReader reader;

    public Cli(Logs logs, Exit exit, ConsoleReader reader) {
        this.LOG = logs;
        this.exit = exit;
        this.reader = reader;
    }

    public void parse(String[] args) {
        if (args.length > 0) {
            int position = 0;
            do {
                position = execute(args, position);
            } while (position < args.length);
        }
    }

    public int execute(String[] args, int position) {
        String arg = args[position];
        if (Arrays.asList("-v", "--version").contains(arg)) {
            printVersion();
            exit.exit(Exit.SUCCESS);
        } else if (Arrays.asList("-h", "--help").contains(arg)) {
            printUsage();
            exit.exit(Exit.SUCCESS);
        } else if (Arrays.asList("-cp", "--create-project").contains(arg)) {
            ProjectCreation projectCreation = new ProjectCreation(
                    new Scanner(System.in),
                    System.out,
                    System.err,
                    reader
            );

            projectCreation.start();
        }

        return position + 1;
    }

    public void printVersion() {
        LOG.info("Current DataReport Version 3.0.0");
        LOG.info("Maintained by Mammad Yahya");
    }

    public void printUsage() {
        LOG.info("Here's the brief introduction");
    }
}
