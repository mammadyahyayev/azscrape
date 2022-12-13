package az.my.datareport.scanner;

import az.my.datareport.ast.DataAST;
import az.my.datareport.config.ConfigFileException;
import az.my.datareport.constant.TestConstants;
import az.my.datareport.converter.StringToEnumConverter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.parser.ConfigFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static az.my.datareport.constant.TestConstants.CONFIG_FILE_PATH;
import static az.my.datareport.constant.TestConstants.EMPTY_CONFIG_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

class JsonConfigFileScannerTest {

    @Test
    void should_throw_exception_when_given_path_invalid() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> scanner.readDataConfig("invalid_path"));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void should_throw_exception_when_given_file_content_is_empty() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        assertThrows(ConfigFileException.class, () -> scanner.readDataConfig(EMPTY_CONFIG_FILE_PATH));
    }

    @Test
    void should_return_ast() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST actual = scanner.readDataConfig(TestConstants.CONFIG_FILE_PATH);
        assertNotNull(actual);
    }

    @DisplayName("it will read report file configurations in given config file")
    @Test
    void testReadFileConfig_whenConfigFileGiven_thenReturnReportFile() {
        ConfigFile configFile = new ConfigFile("config.json", CONFIG_FILE_PATH, "json");
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ReportFile reportFile = scanner.readFileConfig(configFile);
        assertNotNull(reportFile);
        assertNotNull(reportFile.getFiletype());
        assertNotNull(reportFile.getFilename());
        assertNotNull(reportFile.getFileExtension());
    }
}