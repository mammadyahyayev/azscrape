package az.caspian.core.utils;

import az.caspian.core.utils.AbstractFileSystem;
import az.caspian.core.utils.DefaultFileSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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