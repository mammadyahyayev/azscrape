package parser;

import az.my.datareport.parser.StringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    @Test
    void should_return_text_with_underscores() {
        String expected = "Github_Page";
        String actual = StringUtil.replaceAllSymbols("Github Page", '_');
        assertEquals(expected, actual);
    }

}
