package az.my.datareport.cli;

import java.io.PrintStream;

public class ConsolePrinter {
    private static final String COLOR_DEFAULT = "\u001B[0m";
    private static final String COLOR_RED = "\u001B[31m";
    private static final String COLOR_CYAN = "\u001B[36m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    private final PrintStream printStream;

    public ConsolePrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void print(String msg) {
        printStream.print(msg);
    }

    public void println(String msg) {
        printStream.println(msg);
    }

    public void printErr(String errMsg) {
        printStream.print(COLOR_RED + errMsg + COLOR_DEFAULT);
    }

    public void printlnErr(String errMsg) {
        printStream.println(COLOR_RED + errMsg + COLOR_DEFAULT);
    }
}
