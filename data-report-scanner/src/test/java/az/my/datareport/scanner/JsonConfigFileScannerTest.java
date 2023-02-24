package az.my.datareport.scanner;

import az.my.datareport.config.ConfigFileException;
import az.my.datareport.config.ConfigNotValidException;
import az.my.datareport.tree.DataAST;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class JsonConfigFileScannerTest {

    @Test
    void testReadDataConfig_whenInvalidFilePathGiven_throwException() {
        // given
        String invalidFilePath = "invalid-file-path";

        //when
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException configFileException = assertThrows(ConfigFileException.class,
                () -> scanner.readDataConfig(invalidFilePath));

        // then
        assertEquals(FileNotFoundException.class, configFileException.getCause().getClass());
    }

    @Test
    void testReadDataConfig_whenConfigFilePathGiven_thenReturnConfigData() {
        //given
        URL resource = getClass().getResource("/config.json");
        assertNotNull(resource);

        // when
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        DataAST actual = scanner.readDataConfig(resource.getPath());

        // then
        assertNotNull(actual);
        assertNotNull(actual.getDataNode());
    }

    @Test
    void testReadDataConfig_whenFileWithoutDataFieldGiven_thenReturnConfigData() {
        //given
        URL resource = getClass().getResource("/test-resources/file-without-data-field.json");
        assertNotNull(resource);

        // when
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException configFileException = assertThrows(ConfigFileException.class, () -> scanner.readDataConfig(resource.getPath()));

        // then
        assertEquals(ConfigNotValidException.class, configFileException.getCause().getClass());
        assertEquals("config file must contain 'data' field!", configFileException.getCause().getMessage());
    }

    @Test
    void testReadDataConfig_whenInvalidFileTypeGiven_thenThrowException() {
        //given
        URL resource = getClass().getResource("/test-resources/invalid-config-file-format.txt");
        assertNotNull(resource);

        String path = resource.getPath();

        // when
        ConfigFileScanner scanner = new JsonConfigFileScanner();
        ConfigFileException configFileException = assertThrows(ConfigFileException.class, () -> scanner.readDataConfig(path));

        // then
        assertEquals(configFileException.getMessage(),
                String.format("Content not found or given file path %s doesn't refer to json file!", path));
    }

}