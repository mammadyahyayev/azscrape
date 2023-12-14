package az.caspian.core.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

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
