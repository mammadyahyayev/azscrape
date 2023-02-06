package az.my.datareport.config;

import az.my.datareport.constant.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigFileManagerTest {

    ConfigFileManager manager;

    @BeforeEach
    void beforeEach() {
        manager = new ConfigFileManager();
    }

    @Test
    void testGetConfigFile_whenInvalidPathGiven_thenThrowException() {
        Path path = Path.of(TestConstants.TEST_RESOURCES.toString(), "test-config.xlsx");

        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> manager.getConfigFile(path));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testGetConfigFile_whenIncorrectFileExtensionGiven_thenThrowException() {
        Path path = Path.of(TestConstants.TEST_RESOURCES.toString(), "invalid-config-file-format.txt");
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> manager.getConfigFile(path));
        assertEquals(UnsupportedFileFormatException.class, exception.getCause().getClass());
    }

}