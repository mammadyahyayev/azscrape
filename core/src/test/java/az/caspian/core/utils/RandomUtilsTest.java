package az.caspian.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RandomUtilsTest {

  @Test
  void testGenerateRandomKeyWithLength() {
    String key = RandomUtils.generateRandomKey(10);
    assertNotNull(key);
    assertEquals(10, key.length());
  }

  @Test
  void testGenerateRandomKeyWithDefaultLength() {
    String key = RandomUtils.generateRandomKey();
    assertNotNull(key);
    assertEquals(RandomUtils.DEFAULT_KEY_LENGTH, key.length());
  }

}