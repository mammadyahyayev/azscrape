package az.caspian.scrape.templates;

import java.util.Objects;

public record QueryParameter(String key, String value) {
  public QueryParameter(String key, String value) {
    this.key = Objects.requireNonNull(key, "query parameter key cannot be null");
    this.value = value;
  }
}
