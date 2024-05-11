package az.caspian.core.utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.Objects;

import static java.lang.String.format;

public final class UrlUtils {
  private UrlUtils() {

  }

  /**
   * Validates whether URL is valid or not.
   *
   * @param url a URL
   *
   * @return url with type {@link String}
   * @throws IllegalArgumentException when given URL is {@code null} or not valid
   */
  public static String validateUrl(String url) {
    Objects.requireNonNull(url, "Webpage url cannot be null!");

    var urlValidator = new UrlValidator();
    var isValidUrl = urlValidator.isValid(url);
    if (!isValidUrl) {
      throw new IllegalArgumentException(format("Webpage URL is not valid: %s", url));
    }

    return url;
  }
}
