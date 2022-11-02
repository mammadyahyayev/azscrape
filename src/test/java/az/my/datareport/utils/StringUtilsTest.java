package az.my.datareport.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {

    @Test
    void should_return_text_with_underscores() {
        String expected = "Github_Page";
        String actual = StringUtils.replaceAllSymbols("Github Page", '_');
        assertEquals(expected, actual);
    }

}
