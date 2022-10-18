package az.my.datareport.parser;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfigFileValidityTest {

    @Test
    void should_return_json_extension() {
        String extension = FilenameUtils.getExtension("config.json");
        assertEquals("json", extension);
    }

    @Test
    void should_throw_exception_when_given_file_is_not_supported() {
        assertThrows(UnsupportedFileFormatException.class,
                () -> ConfigFileValidity.validate("C:\\Users\\User\\Desktop\\data-report\\src\\main\\resources\\brainstorming.txt"));
    }

    @Test
    void should_throw_exception_when_path_refer_to_directory() {
        assertThrows(IllegalArgumentException.class,
                () -> ConfigFileValidity.validate("C:\\Users\\User\\Desktop\\data-report\\src\\main\\resources"));
    }

}