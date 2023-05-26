package az.caspian.core.model.enumeration;

import az.caspian.core.model.enumeration.ConfigFileExtension;
import az.caspian.core.model.enumeration.FileExtension;
import az.caspian.core.model.enumeration.FileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumerationTest {

    @Test
    void testContains_whenGivenStringFoundAsFileType_returnTrue() {
        assertTrue(FileType.contains("CsV"));
    }

    @Test
    void testContains_whenNullOrEmptyGiven_returnFalse() {
        assertFalse(FileType.contains(null));
        assertFalse(FileType.contains(""));
    }

    @Test
    void testContains_whenGivenStringFoundAsFileType_returnFalse() {
        assertFalse(FileType.contains("Invalid FileType"));
    }

    @Test
    void testContains_whenGivenStringFoundAsFileExtension_returnTrue() {
        assertTrue(FileExtension.contains("JsOn"));
    }

    @Test
    void testContains_whenGivenStringFoundAsFileExtension_returnFalse() {
        assertFalse(FileExtension.contains("Invalid FileExtension"));
    }

    @Test
    void testContains_whenGivenStringFoundAsConfigFileExtension_returnTrue() {
        assertTrue(ConfigFileExtension.contains("JsOn"));
    }

    @Test
    void testContains_whenGivenStringFoundAsConfigFileExtension_returnFalse() {
        assertFalse(ConfigFileExtension.contains("Invalid Config FileExtension"));
    }
}