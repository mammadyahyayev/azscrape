package az.caspian.core.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import az.caspian.core.io.AbstractFileSystem;
import az.caspian.core.io.DefaultFileSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractFileSystemTest {

  static final Path TEST_FILE_PATH = Path.of("src", "test", "resources", "test.json");
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

  @AfterEach
  void clean() throws IOException {
    Files.deleteIfExists(Path.of(TEST_FILE_PATH.toString()));
  }
}
