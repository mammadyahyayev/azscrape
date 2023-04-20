package az.my.datareport.cli;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CliTest {

    @Test
    void givenVersionFlag_whenParsingFlag_thenVerifyExecutionTimes() {
        // given
        Logs logs = mock(Logs.class);
        Exit exit = mock(Exit.class);
        ConsoleReader reader = mock(ConsoleReader.class);
        Cli cli = new Cli(logs, exit, reader);

        // when
        cli.parse(new String[]{"-v"});

        // then
        verify(logs, times(2)).info(anyString());
        verify(exit, times(1)).exit(Exit.SUCCESS);
    }

}