package az.my.datareport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralTests {

    @Test
    void testGetResource_whenFileNameGiven_returnResourceURL() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        String path = "config.json";
        URL resource = classLoader.getResource(path);
        assertNotNull(resource);
    }

    @ParameterizedTest
    @CsvSource({
            "resources/images",
            "/resources/images",
            "./resources/images",
            "src/test/resources/test.json",
    })
    void testIsAbsolute_whenRelativePathsGiven_returnFalse(String path) {
        File file = new File(path);
        assertFalse(file.isAbsolute());
    }

    @Test
    void testSystemGetProperty_whenUserDirGiven_returnAppDirectory() {
        String currentDir = System.getProperty("user.dir");
        String expected = "C:\\Users\\User\\Desktop\\data-report";
        assertEquals(expected, currentDir);
    }

}
