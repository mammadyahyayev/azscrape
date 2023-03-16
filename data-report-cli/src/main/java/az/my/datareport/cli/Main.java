package az.my.datareport.cli;

import az.my.datareport.config.DataReportProjectConfiguration;

public class Main {
    public static void main(String[] args) {
        DataReportProjectConfiguration configuration = new DataReportProjectConfiguration();
        configuration.init();

        Logs logs = new Logs(System.out, System.err);
        Exit exit = new Exit();
        Cli cli = new Cli(logs, exit);
        cli.parse(args);
    }
}
