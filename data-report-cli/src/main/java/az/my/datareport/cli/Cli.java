package az.my.datareport.cli;

import az.my.datareport.config.DataReportProjectConfiguration;

import java.util.Arrays;

public class Cli {
    private final DataReportProjectConfiguration configuration;
    private final Logs logs;
    private final Exit exit;
    private final ConsoleReader reader;

    public Cli(DataReportProjectConfiguration configuration, Logs logs, Exit exit, ConsoleReader reader) {
        this.configuration = configuration;
        this.logs = logs;
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
            ProjectCreation projectCreation = new ProjectCreation(configuration, reader, logs);
            projectCreation.start();
            exit.exit(Exit.SUCCESS);
        }

        return position + 1;
    }

    public void printVersion() {
        logs.info("Current DataReport Version 3.0.0");
        logs.info("Maintained by Mammad Yahya");
    }

    public void printUsage() {
        logs.info("Here's the brief introduction");
    }
}
