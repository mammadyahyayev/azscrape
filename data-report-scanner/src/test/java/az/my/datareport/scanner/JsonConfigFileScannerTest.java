package az.my.datareport.scanner;

import az.my.datareport.config.ConfigFile;
import az.my.datareport.config.ConfigFileException;
import az.my.datareport.constant.TestConstants;
import az.my.datareport.model.ReportFile;
import az.my.datareport.tree.DataAST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static az.my.datareport.constant.TestConstants.CONFIG_FILE_PATH;
import static az.my.datareport.constant.TestConstants.EMPTY_CONFIG_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

class JsonConfigFileScannerTest {

    @Test
    void testReadDataConfig_whenPathInvalid_throwException() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> scanner.readDataConfig("invalid_path"));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testReadDataConfig_whenFileIsEmpty_throwException() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        assertThrows(ConfigFileException.class, () -> scanner.readDataConfig(EMPTY_CONFIG_FILE_PATH.toString()));
    }

    @Test
    void testReadDataConfig_whenTrueFileGiven_returnParsedDataAsAST() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST actual = scanner.readDataConfig(TestConstants.CONFIG_FILE_PATH.toString());
        assertNotNull(actual);
    }

    @DisplayName("it will read report file configurations in given config file")
    @Test
    void testReadFileConfig_whenConfigFileGiven_thenReturnReportFile() {
        ConfigFile configFile = new ConfigFile("config.json", CONFIG_FILE_PATH.toString(), "json");
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ReportFile reportFile = scanner.readFileConfig(configFile);
        assertNotNull(reportFile);
        assertNotNull(reportFile.getFiletype());
        assertNotNull(reportFile.getFilename());
        assertNotNull(reportFile.getFileExtension());
    }
}