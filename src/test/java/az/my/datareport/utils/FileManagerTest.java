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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileManagerTest {

    final static Path TEST_FILE_PATH = Path.of("src", "test", "resources", "test.json");
    FileManager manager;

    @BeforeEach
    void setup() {
        manager = new FileManager();
    }

    @Test
    void testConstructDirectory_whenExistPathGiven_returnFolderWithGivenPath() {
        Path path = Path.of("src", "test", "resources");
        File expected = new File(path.toString());
        File actual = manager.constructDirectory(path.toString());
        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
    }

    @Test
    void testConstructDirectory_whenFileGiven_throwException() throws IOException {
        createTestFile();
        assertThrows(DataReportAppException.class, () -> manager.constructDirectory(TEST_FILE_PATH.toString()));
    }

    @ParameterizedTest
    @MethodSource("nonExistentDirectoryPaths")
    void testConstructDirectory_whenNonExistentPathGiven_createAndReturnFileWithGivenPath(String nonExistentPath) {
        File actual = manager.constructDirectory(nonExistentPath);
        File expected = new File(nonExistentPath);

        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
    }

    private static Stream<Arguments> nonExistentDirectoryPaths() {
        Path testPath1 = Path.of("src", "test", "resources", "images");
        Path testPath2 = Path.of("src", "test", "resources", "images2", "new");
        return Stream.of(
                Arguments.of(testPath1.toString()),
                Arguments.of(testPath2.toString())
        );
    }

    @Test
    void testConstructFile_whenExistPathGiven_returnFileWithGivenPath() throws IOException {
        File expected = createTestFile();
        File actual = manager.constructFile(expected.getPath());
        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
    }

    @Test
    void testConstructFile_whenDirectoryPathGiven_throwException() {
        Path path = Path.of("src", "test", "resources");
        String message = assertThrows(DataReportAppException.class, () -> manager.constructFile(path.toString())).getMessage();
        String expected = "Given path [ " + path + " ] isn't file!";
        assertEquals(expected, message);
    }

    @Test
    void testConstructFile_whenNonExistPathGiven_returnFileWithGivenPath() {
        Path path = Path.of("src", "test", "resources", "test.xlsx");
        File expected = new File(path.toString());
        File file = manager.constructFile(path.toString());
        assertEquals(expected.getAbsolutePath(), file.getAbsolutePath());
    }

    private File createTestFile() throws IOException {
        File file = new File(TEST_FILE_PATH.toString());
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    @AfterEach
    void clean() throws IOException {
        File file = new File(TEST_FILE_PATH.toString());
        if (file.exists()) {
            file.delete();
        }
    }
}