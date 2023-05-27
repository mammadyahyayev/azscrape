package az.caspian.scrape.templates;

import java.util.Objects;

public class QueryParameter {
    private final String key;
    private final String value;

    public QueryParameter(String key, String value) {
        this.key = Objects.requireNonNull(key, "query parameter key cannot be null");
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
