package az.my.datareport.cli;

import az.my.datareport.services.OwnerService;
import az.my.datareport.services.OwnerServiceImpl;
import az.my.datareport.services.ProjectService;
import az.my.datareport.services.ProjectServiceImpl;
import az.my.datareport.utils.PropertiesFileSystem;

import java.util.Arrays;

public class Cli {
    private final Logs logs;
    private final Exit exit;
    private final ConsoleReader reader;

    public Cli(Logs logs, Exit exit, ConsoleReader reader) {
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
            ProjectService projectService = new ProjectServiceImpl(new PropertiesFileSystem());
            ProjectCreation projectCreation = new ProjectCreation(projectService, reader, logs);
            projectCreation.create();
            exit.exit(Exit.SUCCESS);
        } else if (Arrays.asList("-a", "--auth").contains(arg)) {
            OwnerService ownerService = new OwnerServiceImpl(new PropertiesFileSystem());
            OwnerCreation ownerCreation = new OwnerCreation(ownerService, reader, logs);
            ownerCreation.create();
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
