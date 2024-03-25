package az.caspian.core.utils;

import java.util.Random;

public final class RandomUtils {
  static final int DEFAULT_KEY_LENGTH = 6;

  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final String NUMBERS = "0123456789";

  public static String generateRandomKey() {
    return generateRandomKey(DEFAULT_KEY_LENGTH);
  }

  public static String generateRandomKey(int length) {
    Random random = new Random();
    StringBuilder keyBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      boolean useLetter = random.nextBoolean();
      String source = useLetter ? LETTERS : NUMBERS;

      int index = random.nextInt(source.length());
      char randomChar = source.charAt(index);
      keyBuilder.append(randomChar);
    }

    return keyBuilder.toString();
  }

  private RandomUtils() {
  }
}
