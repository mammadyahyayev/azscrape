package az.caspian.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

  @Test
  void testReplaceAllSymbols_whenTextWithUnderlineGiven_replaceUnderlineWithSpace() {
    String expected = "Github_Page";
    String actual = StringUtils.replaceAllSymbols("Github Page", '_');
    assertEquals(expected, actual);
  }

  @Test
  void testIsNullOrEmpty_returnTrueWhenStrContainsWhiteSpaces() {
    assertTrue(StringUtils.isNullOrEmpty("      "));
  }
}
