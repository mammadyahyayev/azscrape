package az.my.datareport.tree;

import java.util.Objects;

public class QueryParameter {
    private final String key;
    private final String value;
    private final boolean isPageParameter;

    public QueryParameter(String key, String value, boolean isPageParameter) {
        this.key = Objects.requireNonNull(key, "query parameter key cannot be null");
        this.value = value;
        this.isPageParameter = isPageParameter;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isPageParameter() {
        return isPageParameter;
    }
}
