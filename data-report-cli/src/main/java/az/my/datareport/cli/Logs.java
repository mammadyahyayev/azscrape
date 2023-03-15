package az.my.datareport.cli;

import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logs {

    private final DateTimeFormatter timeFormatter;
    private final PrintStream stdOut;
    private final PrintStream stdErr;

    public Logs(PrintStream stdOut, PrintStream stdErr) {
        this.stdOut = stdOut;
        this.stdErr = stdErr;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    }

    public void info(String message) {
        print(this.stdOut, "[INFO] " + message);
    }

    public void error(String message) {
        print(this.stdErr, "[ERROR] " + message);
    }

    public void print(PrintStream stream, String message) {
        LocalTime currentTime = LocalTime.now();
        String timestamp = currentTime.format(timeFormatter);
        stream.println(timestamp + " " + message);
    }
}
