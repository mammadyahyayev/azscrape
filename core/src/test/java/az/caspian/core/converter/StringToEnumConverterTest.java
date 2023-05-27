package az.caspian.core.converter;

import az.caspian.core.converter.ConversionFailedException;
import az.caspian.core.converter.StringToEnumConverter;
import az.caspian.core.model.enumeration.FileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringToEnumConverterTest {

    @Test
    void testConvert_whenNonExistEnumTypeGiven_throwException() {
        assertThrows(ConversionFailedException.class,
                () -> StringToEnumConverter.convert("invalid_source", FileType.class));
    }

    @Test
    void testConvert_whenExistedEnumTypeGivenAsString_returnEnum() {
        String expectedType = "JSON";
        FileType fileType = StringToEnumConverter.convert(expectedType, FileType.class);
        assertEquals(fileType.name(), expectedType);
    }

}