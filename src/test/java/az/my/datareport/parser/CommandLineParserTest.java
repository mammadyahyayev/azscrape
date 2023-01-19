package az.my.datareport.parser;

import az.my.datareport.constant.TestConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    TODO:
        1. Change method names
        2. There is a scenario, user doesn't give equal sign (key-value combination), or user gave but its value is not exist.
           In this case check if the key is required and value is empty throw exception. And user give some spaces between flag and key
           or key and value, therefore trim values.
        3. Assert exception messages where it has been thrown
        4. Test getRequiredKeys method
 */
class CommandLineParserTest {

    @Test
    void testParse_whenArgumentsGiven_returnAppConfig() {
        // given
        String[] cliArguments = new String[]{
                "-Dconfig.file.path=" + TestConstants.CONFIG_FILE_PATH,
                "-Doutput.file.path=" + TestConstants.TEST_RESOURCES
        };
        AppConfig expected = new AppConfig(TestConstants.CONFIG_FILE_PATH.toString(), TestConstants.TEST_RESOURCES.toString());

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        AppConfig actual = parser.parse();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void testParse_whenFlagIsNotGiven_throwException() {
        // given
        String[] cliArguments = new String[]{
                "config.file.path=" + TestConstants.CONFIG_FILE_PATH,
        };

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        assertThrows(RuntimeException.class, parser::parse);
    }

    @Test
    void testParse_whenFlagIsNotGiven2_throwException() {
        // given
        String[] cliArguments = new String[]{
                "-Dconfig.file.path " + TestConstants.CONFIG_FILE_PATH,
        };

        // when
        CommandLineParser parser = new CommandLineParser(cliArguments);
        assertThrows(RuntimeException.class, parser::parse);
    }
}