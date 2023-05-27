package az.caspian.scrape.templates.pagination;

import az.caspian.scrape.templates.QueryParameter;
import az.caspian.core.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class PageParameters {
    public static final String PAGE_SPECIFIER = "{pageNum}";
    private static final String QUERY_PARAM_SEPARATOR = "&";

    private String pageUrl;
    private final int minPage;
    private final int maxPage;
    private final int delayBetweenPages;
    private final List<QueryParameter> queryParameters;
    private PageSpecifier pageSpecifier;

    private PageParameters(Builder builder) {
        this.pageUrl = builder.pageUrl;
        this.queryParameters = builder.queryParameters;
        this.minPage = builder.minPage;
        this.maxPage = builder.maxPage;
        this.delayBetweenPages = builder.delayBetweenPages;
        buildPageUrl();
    }

    //region Getters & Setters
    public String getPageUrl(int pageNumber) {
        return this.pageUrl.replace(PAGE_SPECIFIER, String.valueOf(pageNumber));
    }

    public int getMinPage() {
        return minPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getDelayBetweenPages() {
        return delayBetweenPages;
    }

    //endregion

    private void buildPageUrl() {
        String url = this.pageUrl;
        String queryParams = queryParameters.stream().map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining(QUERY_PARAM_SEPARATOR));

        if (pageSpecifier == null || pageSpecifier.type == PageSpecifierType.NOT_SET) {
            var pageSpecifierDetection = new PageSpecifierDetection(url);
            pageSpecifier = pageSpecifierDetection.detect();
        }

        if (pageSpecifier.type == PageSpecifierType.QUERY_PARAM) {
            if (queryParameters.size() > 0)
                url += QUERY_PARAM_SEPARATOR + queryParams;

        } else if (pageSpecifier.type == PageSpecifierType.HASH_FRAGMENT) {
            String fragmentPart = url.substring(pageSpecifier.begin, pageSpecifier.end + 1);

            if (queryParameters.size() > 0) {
                String queryParamsPart = url.substring(0, pageSpecifier.begin) + "?" + queryParams;
                url = queryParamsPart + fragmentPart;
            }
        } else {
            if (queryParameters.size() > 0)
                url += "?" + queryParams;
        }

        this.pageUrl = url;
    }

    public static class Builder {
        private String pageUrl;
        private final List<QueryParameter> queryParameters;
        private int minPage;
        private int maxPage;
        private int delayBetweenPages;

        {
            queryParameters = new ArrayList<>();
        }

        public Builder url(String url) {
            this.pageUrl = url;
            return this;
        }

        public Builder pageRange(int minPage, int maxPage) {
            if (minPage < 0 || maxPage < 0 || minPage > maxPage) {
                throw new IllegalArgumentException(
                        format("Range must be greater or equal than 0 and minPage (%d) must be less than maxPage (%d)",
                                minPage, maxPage)
                );
            }

            this.minPage = minPage;
            this.maxPage = maxPage;
            return this;
        }

        public Builder delayBetweenPages(int delayInMs) {
            if (delayInMs < 0) {
                delayInMs = 0;
            }

            this.delayBetweenPages = delayInMs;
            return this;
        }

        public Builder queryParam(String key, String value) {
            if (StringUtils.isNullOrEmpty(key) || StringUtils.isNullOrEmpty(value)) {
                throw new IllegalArgumentException(
                        format("key (%s) and value (%s) cannot be null or empty", key, value)
                );
            }

            queryParameters.add(new QueryParameter(key, value));

            return this;
        }

        public PageParameters build() {
            return new PageParameters(this);
        }
    }

    private enum PageSpecifierType {
        NOT_SET, QUERY_PARAM, URL_PATH, HASH_FRAGMENT
    }

    private static class PageSpecifier {
        private final PageSpecifierType type;
        private final int begin;
        private final int end;

        PageSpecifier(PageSpecifierType type, int begin, int end) {
            this.type = type;
            this.begin = begin;
            this.end = end;
        }
    }

    private static class PageSpecifierDetection {
        private static final String PAGE_SPECIFIER_KEY = "pageNum";

        private static final char HASH_FRAGMENT_SPECIFIER = '#';
        private static final char QUERY_PARAM_SPECIFIER = '?';

        private final String url;

        PageSpecifierDetection(String url) {
            this.url = Objects.requireNonNull(url);
        }

        public PageSpecifier detect() {
            int begin = url.indexOf('{');
            int end = url.indexOf('}');

            if (begin == -1 || end == -1 || begin > end) {
                throw new IllegalArgumentException(
                        format("Page specifier isn't configured correctly!, begin=%d, end=%d", begin, end)
                );
            }

            int pageSpecifierKey = url.indexOf(PAGE_SPECIFIER_KEY);
            if (pageSpecifierKey == -1 || pageSpecifierKey < begin || pageSpecifierKey > end) {
                throw new IllegalArgumentException(
                        format("Page specifier is in wrong place!, begin=%d, pageKey=%d, end=%d",
                                begin, pageSpecifierKey, end)
                );
            }

            int hashFragmentSpecIndex = url.indexOf(HASH_FRAGMENT_SPECIFIER);
            if (hashFragmentSpecIndex != -1) {
                return new PageSpecifier(PageSpecifierType.HASH_FRAGMENT, hashFragmentSpecIndex, end);
            }

            int queryParamSpecIndex = url.indexOf(QUERY_PARAM_SPECIFIER);
            if (queryParamSpecIndex != -1) {
                return new PageSpecifier(PageSpecifierType.QUERY_PARAM, queryParamSpecIndex, end);
            }

            return new PageSpecifier(PageSpecifierType.URL_PATH, begin, end);
        }

    }

}
