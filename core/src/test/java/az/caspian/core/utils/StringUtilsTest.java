package az.caspian.core.utils;

import az.caspian.core.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {

    @Test
    void testReplaceAllSymbols_whenTextWithUnderlineGiven_replaceUnderlineWithSpace() {
        String expected = "Github_Page";
        String actual = StringUtils.replaceAllSymbols("Github Page", '_');
        assertEquals(expected, actual);

        StringUtils.isNullOrEmpty("null");
    }

}
