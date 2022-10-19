package parser;

import az.my.datareport.constant.TestConstants;
import az.my.datareport.parser.ConfigReader;
import az.my.datareport.parser.ConfigReaderFactory;
import az.my.datareport.parser.UnsupportedFileFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONConfigReaderTest {

    @Test
    void should_throw_exception_when_generated_file_type_is_not_supported() {
        ConfigReader reader = ConfigReaderFactory.getReader(TestConstants.TEST_CONFIG_FILE_PATH);
        assertThrows(UnsupportedFileFormatException.class, reader::read);
    }

}