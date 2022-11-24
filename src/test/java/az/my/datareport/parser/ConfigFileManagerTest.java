package az.my.datareport.parser;

import az.my.datareport.config.ConfigFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

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
        String path = "C:\\Users\\User\\Desktop\\data-report\\src\\test\\resources\\test-config.xlsx";
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> manager.getConfigFile(path));
        assertEquals(FileNotFoundException.class, exception.getCause().getClass());
    }

    @Test
    void testGetConfigFile_whenIncorrectFileExtensionGiven_thenThrowException() {
        String path = "C:\\Users\\User\\Desktop\\data-report\\src\\test\\resources\\invalid-config-file-format.txt";
        ConfigFileException exception = assertThrows(ConfigFileException.class, () -> manager.getConfigFile(path));
        assertEquals(UnsupportedFileFormatException.class, exception.getCause().getClass());
    }

}