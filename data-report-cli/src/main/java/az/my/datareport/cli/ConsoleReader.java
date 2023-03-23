package az.my.datareport.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ConsoleReader {
    private final PrintStream stdOut;
    private final PrintStream stdErr;
    private final BufferedReader reader;

    public ConsoleReader(PrintStream stdOut, PrintStream stdErr, BufferedReader reader) {
        this.stdOut = stdOut;
        this.stdErr = stdErr;
        this.reader = reader;
    }


    /**
     * Reads inputs from console that supplied by end user.
     *
     * @param message question for end user
     * @return input that given from end user
     */
    public String readLine(String message) {
        stdOut.print(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            stdErr.println("Error happened: " + e);
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
                stdOut.print(message);
                String input = reader.readLine();
                if (input == null || input.isEmpty() || input.isBlank()) {
                    stdErr.println(exceptionMessage);
                    currentTry++;
                    continue;
                }

                return input;
            } catch (IOException e) {
                stdErr.println("Error happened: " + e);
                throw new RuntimeException(e);
            }
        }

        throw new IllegalArgumentException(exceptionMessage);
    }
}
