package az.caspian.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
  public static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

  public static String ofDefaultFormat(LocalDateTime dateTime) {
    return dateTime.format(DEFAULT_DATE_FORMAT);
  }

  private DateUtils() {
  }
}
