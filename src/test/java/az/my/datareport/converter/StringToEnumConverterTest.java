package az.my.datareport.converter;

import az.my.datareport.model.enumeration.FileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringToEnumConverterTest {

    @Test
    void should_throw_exception_when_source_and_target_does_not_match() {
        assertThrows(ConversionFailedException.class,
                () -> StringToEnumConverter.convert("invalid_source", FileType.class));
    }

    @Test
    void should_return_matched_enum_type() {
        String expectedType = "JSON";
        FileType fileType = StringToEnumConverter.convert(expectedType, FileType.class);
        assertEquals(fileType.name(), expectedType);
    }

}