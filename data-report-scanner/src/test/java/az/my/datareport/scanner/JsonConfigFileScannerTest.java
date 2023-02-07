package az.my.datareport.scanner;

import az.my.datareport.config.ConfigFile;
import az.my.datareport.config.ConfigFileException;
import az.my.datareport.model.ReportFile;
import az.my.datareport.tree.DataAST;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class JsonConfigFileScannerTest {

    static String configFilePath = "";

    @BeforeAll
    static void setUp() {
        ClassLoader classLoader = JsonConfigFileScannerTest.class.getClassLoader();
        URL resource = classLoader.getResource("config.json");
        if (resource == null) {
            fail("config.json was not found");
        }

        configFilePath = resource.getPath();
    }

    @Test
    void testReadDataConfig_whenPathInvalid_throwException() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> scanner.readDataConfig("invalid_path"));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testReadDataConfig_whenFileIsEmpty_throwException() {
        //given
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("test-resources/empty-config.json");
        if (resource == null) {
            fail("empty-config.json was not found");
        }

        String path = resource.getPath();
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        assertThrows(ConfigFileException.class, () -> scanner.readDataConfig(path));
    }

    @Test
    void testReadDataConfig_whenTrueFileGiven_returnParsedDataAsAST() {
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST actual = scanner.readDataConfig(configFilePath);
        assertNotNull(actual);
    }

    @DisplayName("it will read report file configurations in given config file")
    @Test
    void testReadFileConfig_whenConfigFileGiven_thenReturnReportFile() {
        ConfigFile configFile = new ConfigFile("config.json", configFilePath, "json");

        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ReportFile reportFile = scanner.readFileConfig(configFile);
        assertNotNull(reportFile);
        assertNotNull(reportFile.getFiletype());
        assertNotNull(reportFile.getFilename());
        assertNotNull(reportFile.getFileExtension());
    }
}