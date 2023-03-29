package az.my.datareport.cli;

import az.my.datareport.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader {
    private final ConsolePrinter printer;
    private final BufferedReader reader;

    public ConsoleReader(ConsolePrinter printer, BufferedReader reader) {
        this.printer = printer;
        this.reader = reader;
    }


    /**
     * Reads inputs from console that supplied by end user.
     *
     * @param message question for end user
     * @return input that given from end user can be empty or null
     */
    public String readLine(String message) {
        printer.print(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            printer.printlnErr("Error happened: " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads inputs from console that supplied by end user, and the input
     * must be not null or empty.
     *
     * @param message          question for end user
     * @param tryCount         indicates how many times to question will be asked in case if user
     *                         enter empty or null input
     * @param exceptionMessage an exception message that will be shown if user fails to enter input
     *                         that in a given amount of try count
     * @return input that given from end user
     * @throws IllegalArgumentException throws when tryCount is exceeded or user fails to enter desired input
     */
    public String readLine(String message, int tryCount, String exceptionMessage) {
        int currentTry = 0;
        while (currentTry < tryCount) {
            try {
                printer.print(message);
                String input = reader.readLine();
                if (StringUtils.isNullOrEmpty(input)) {
                    printer.printlnErr(exceptionMessage);
                    currentTry++;
                    continue;
                }

                return input;
            } catch (IOException e) {
                printer.printlnErr("Error happened: " + e);
                throw new RuntimeException(e);
            }
        }

        throw new IllegalArgumentException(exceptionMessage);
    }
}
