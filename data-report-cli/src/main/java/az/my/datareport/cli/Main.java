package az.my.datareport.cli;

import az.my.datareport.config.DataReportProjectConfiguration;
import az.my.datareport.utils.AbstractFileSystem;
import az.my.datareport.utils.DefaultFileSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        AbstractFileSystem abstractFileSystem = new DefaultFileSystem();
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration(abstractFileSystem);
        configuration.createYmDirectory();
        configuration.createYmPropertiesFile();

        PrintStream out = System.out;
        PrintStream err = System.err;
        Logs logs = new Logs(out, err);
        Exit exit = new Exit();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ConsoleReader consoleReader = new ConsoleReader(new ConsolePrinter(out), reader);
        Cli cli = new Cli(configuration, logs, exit, consoleReader);
        cli.parse(args);
    }
}
