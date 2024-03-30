package az.caspian.scrape.templates.scroll;

import az.caspian.core.utils.StringUtils;
import az.caspian.scrape.templates.QueryParameter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class ScrollablePageParameters {
    private final String url;
    private final List<QueryParameter> queryParameters;

    public ScrollablePageParameters(Builder builder) {
        this.url = builder.pageUrl;
        this.queryParameters = builder.queryParameters;
    }

    public String getUrl() {
        return url;
    }

    public List<QueryParameter> getQueryParameters() {
        return queryParameters;
    }

    public static class Builder {
        private final List<QueryParameter> queryParameters;
        private String pageUrl;

        {
            queryParameters = new ArrayList<>();
        }

        public ScrollablePageParameters.Builder url(String url) {
            this.pageUrl = url;
            return this;
        }

        public ScrollablePageParameters.Builder queryParam(String key, String value) {
            if (StringUtils.isNullOrEmpty(key) || StringUtils.isNullOrEmpty(value)) {
                throw new IllegalArgumentException(
                        format("key (%s) and value (%s) cannot be null or empty", key, value)
                );
            }

            queryParameters.add(new QueryParameter(key, value));

            return this;
        }

        public ScrollablePageParameters build() {
            return new ScrollablePageParameters(this);
        }
    }
}
