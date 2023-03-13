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

class FileManagerTest {

    final static Path TEST_FILE_PATH = Path.of("src", "test", "resources", "test.json");
    FileManager manager;

    @BeforeEach
    void setup() {
        manager = new FileManager();
    }

    @Test
    void testCreateDirectory_whenExistPathGiven_returnFolderWithGivenPath() {
        Path path = Path.of("src", "test", "resources");
        File expected = new File(path.toString());
        File actual = manager.createDirectory(path.toString());
        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
    }

    @Test
    void testCreateDirectory_whenFileGiven_throwException() throws IOException {
        createTestFile();
        assertThrows(DataReportAppException.class, () -> manager.createDirectory(TEST_FILE_PATH.toString()));
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

    @ParameterizedTest
    @MethodSource("nonExistentDirectoryPaths")
    void testCreateDirectory_whenNonExistentPathGiven_createAndReturnFileWithGivenPath(String nonExistentPath) {
        File actual = manager.createDirectory(nonExistentPath);
        File expected = new File(nonExistentPath);

        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());

        // delete newly generated folders
        deleteFile(nonExistentPath);
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
    void testCreateFile_whenExistPathGiven_returnFileWithGivenPath() throws IOException {
        File expected = createTestFile();
        File actual = manager.createFile(expected.getPath());
        assertEquals(expected.getAbsolutePath(), actual.getAbsolutePath());
        assertNotNull(manager.getFile(expected.getAbsolutePath()));
        assertTrue(manager.deleteFile(expected.toPath()));
    }

    @Test
    void testCreateFile_whenDirectoryPathGiven_throwException() {
        Path path = Path.of("src", "test", "resources");
        String message = assertThrows(DataReportAppException.class, () -> manager.createFile(path.toString())).getMessage();
        String expected = "Given path [ " + path + " ] isn't file!";
        assertEquals(expected, message);
    }

    @Test
    void testCreateFile_whenNonExistPathGiven_returnFileWithGivenPath() {
        Path path = Path.of("src", "test", "resources", "test.xlsx");
        File expected = new File(path.toString());
        File file = manager.createFile(path.toString());
        assertEquals(expected.getAbsolutePath(), file.getAbsolutePath());

        // delete test.xlsx file
        deleteFile(path.toString());
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