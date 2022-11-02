package az.my.datareport.scanner;

import az.my.datareport.ast.DataAST;
import az.my.datareport.config.ConfigFileException;
import az.my.datareport.constant.TestConstants;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static az.my.datareport.constant.TestConstants.EMPTY_CONFIG_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

class JsonConfigFileScannerTest {

    @Test
    void should_throw_exception_when_given_path_invalid() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> scanner.read("invalid_path"));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void should_throw_exception_when_given_file_content_is_empty() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        assertThrows(ConfigFileException.class, () -> scanner.read(EMPTY_CONFIG_FILE_PATH));
    }

    @Test
    void should_return_ast() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST actual = scanner.read(TestConstants.CONFIG_FILE_PATH);
        assertTrue(actual.getDataNodes().size() > 0);
    }

}