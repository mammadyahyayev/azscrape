package az.my.datareport.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsoleReaderTest {

    private PrintStream stdOut;
    private PrintStream stdErr;
    private BufferedReader reader;
    private ConsoleReader consoleReader;

    @BeforeEach
    public void setUp() {
        reader = mock(BufferedReader.class);
        stdOut = mock(PrintStream.class);
        stdErr = mock(PrintStream.class);
        consoleReader = new ConsoleReader(stdOut, stdErr, reader);
    }

    @Test
    void testReadline_whenQuestionAsked_thenReturnUserInput() throws IOException {
        // given

        // when
        when(reader.readLine()).thenReturn("Test");
        String actual = consoleReader.readLine("Enter project name:");

        // then
        assertEquals("Test", actual);
    }

    @Test
    void testReadline_whenNullInputGiven_thenReturnNull() throws IOException {
        // given

        // when
        when(reader.readLine()).thenReturn(null);
        String input = consoleReader.readLine("Enter project name:");

        // then
        assertNull(input);
    }

    @Test
    void testReadline_whenExceedNullInputTryCount_thenThrowException() throws IOException {
        // given
        String message = "Enter project name:";
        String exMessage = "You must enter answer!";
        int tryCount = 3;

        // when
        when(reader.readLine()).thenReturn(null);

        // then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> consoleReader.readLine(message, tryCount, exMessage));

        assertEquals(exMessage, ex.getMessage());
        verify(stdOut, times(tryCount)).print(message);
        verify(stdErr, times(tryCount)).println("\n" + exMessage);
    }

}