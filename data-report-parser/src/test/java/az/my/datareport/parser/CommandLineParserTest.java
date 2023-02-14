package az.my.datareport.parser;

import az.my.datareport.constant.FileConstants;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/*
    TODO:
        3. Assert exception messages where it has been thrown
        4. Test requiredKeys method
 */
class CommandLineParserTest {

    @Test
    void testParse_whenArgumentsGiven_returnAppConfig() {
        // given
        String[] cliArguments = new String[]{
                "-Dconfig.file.path=" + FileConstants.CONFIG_FILE_PATH,
                "-Doutput.file.path=" + FileConstants.TEST_RESOURCES
        };

        Map<String, String> config = new HashMap<>();
        config.put("config.file.path", FileConstants.CONFIG_FILE_PATH.toString());
        config.put("output.file.path", FileConstants.TEST_RESOURCES.toString());

        AppConfig expected = new AppConfig(config);
        expected.load();

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        AppConfig actual = parser.parse();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void testParse_whenRequiredKeyMissing_throwException() {
        // given
        String[] cliArguments = new String[]{
                "-Doutput.file.path=" + FileConstants.TEST_RESOURCES
        };

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        assertThrows(ParseException.class, parser::parse);
    }

    @Test
    void testParse_whenOptionalKeyMissing_thenConfigPropertyMustBeNull() {
        // given
        String[] cliArguments = new String[]{
                "-Dconfig.file.path=" + FileConstants.CONFIG_FILE_PATH,
        };

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        AppConfig config = parser.parse();
        assertNull(config.getOutputFilePath());
    }

    @Test
    void testParse_whenFlagIsNotGiven_throwException() {
        // given
        String[] cliArguments = new String[]{
                "config.file.path=" + FileConstants.CONFIG_FILE_PATH,
        };

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        assertThrows(ParseException.class, parser::parse);
    }

    @Test
    void testParse_whenSpacesBetweenKeyAndValue_resultShouldNotBeAffected() {
        // given
        String[] cliArguments = new String[]{
                "-Dconfig.file.path = " + FileConstants.CONFIG_FILE_PATH,
        };

        AppConfig expected = new AppConfig(FileConstants.CONFIG_FILE_PATH.toString(), null);

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        AppConfig actual = parser.parse();

        assertEquals(expected, actual);
    }

    @Test
    void testParse_whenSpacesBetweenFlagAndProperty_resultShouldNotBeAffected() {
        // given
        String[] cliArguments = new String[]{
                "-D   config.file.path = " + FileConstants.CONFIG_FILE_PATH,
        };

        AppConfig expected = new AppConfig(FileConstants.CONFIG_FILE_PATH.toString(), null);

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        AppConfig actual = parser.parse();

        assertEquals(expected, actual);
    }
}