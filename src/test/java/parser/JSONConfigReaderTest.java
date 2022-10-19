package parser;

import az.my.datareport.constant.TestConstants;
import az.my.datareport.parser.ConfigReader;
import az.my.datareport.parser.ConfigReaderFactory;
import az.my.datareport.parser.UnsupportedFileFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JSONConfigReaderTest {

    ConfigReader reader;

    @BeforeEach
    public void setUp() {
        reader = ConfigReaderFactory.getReader(TestConstants.TEST_CONFIG_FILE_PATH);
    }

    @Test
    void should_throw_exception_when_generated_file_type_is_not_supported() {
        assertThrows(UnsupportedFileFormatException.class, reader::read);
    }

}