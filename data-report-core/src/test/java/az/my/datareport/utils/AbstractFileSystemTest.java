package az.my.datareport.utils;

import az.my.datareport.DataReportAppException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFileSystemTest {

    final static Path TEST_FILE_PATH = Path.of("src", "test", "resources", "test.json");
    AbstractFileSystem manager;

    @BeforeEach
    void setup() {
        manager = new DefaultFileSystem();
    }

    @Test
    void testCreateFileName_whenUnformattedFileNameGiven_returnFormattedName() {
        String expected = "test__file";
        String actual = manager.createFilename("TeSt  FILE");
        assertEquals(expected, actual);
    }

    @Test
    void testCreateFileName_whenUnformattedFileNameAndExtensionGiven_returnFileFullName() {
        String expected = "test__file.json";
        String actual = manager.createFilename("TeSt  FILE", "JSoN");
        assertEquals(expected, actual);
    }

    private File createTestFile() throws IOException {
        File file = new File(TEST_FILE_PATH.toString());
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void clean() throws IOException {
        File file = new File(TEST_FILE_PATH.toString());
        if (file.exists()) {
            file.delete();
        }
    }
}