package az.my.datareport.cli;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CliTest {
    private Exit exit = new Exit();
    private Logs logs = new Logs(System.out, System.err);
    private Cli cli = new Cli(logs, exit);

    @Test
    void should_printVersionInfo() {
        logs = mock(Logs.class);
        exit = mock(Exit.class);
        cli = new Cli(logs, exit);
        cli.parse(new String[]{"-v"});
        verify(logs, times(2)).info(anyString());
        verify(exit, times(1)).exit(Exit.SUCCESS);
    }

}