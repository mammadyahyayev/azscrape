package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class PageParameters {
    private String pageUrl;
    private final List<QueryParameter> queryParameters;
    private int minPage;
    private int maxPage;
    private int delayBetweenPages;
    private PageSpecifier pageSpecifier;

    public PageParameters() {
        this.queryParameters = new ArrayList<>();
    }

    public void addQueryParameter(QueryParameter queryParameter) {
        Asserts.required(queryParameter, "queryParam cannot be null!");

        queryParameters.add(queryParameter);
    }

    //region Getters & Setters

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getMinPage() {
        return minPage;
    }

    public void setMinPage(int minPage) {
        this.minPage = minPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getDelayBetweenPages() {
        return delayBetweenPages;
    }

    public void setDelayBetweenPages(int delayInMs) {
        this.delayBetweenPages = delayInMs;
    }

    //endregion

    private void buildPageUrl() {
        String url = this.pageUrl;
        String queryParams = queryParameters.stream().map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        if (pageSpecifier == null || pageSpecifier.type == PageSpecifierType.NOT_SET) {
            var pageSpecifierDetection = new PageSpecifierDetection(url);
            pageSpecifier = pageSpecifierDetection.detect();
        }

        if (pageSpecifier.type == PageSpecifierType.QUERY_PARAM) {
            if (queryParameters.size() > 0)
                url += "&" + queryParams;

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

    public String getPageUrl(int pageNumber) {
        return this.pageUrl.replace("{pageNum}", String.valueOf(pageNumber));
    }

    public void build() {
        buildPageUrl();
    }

    enum PageSpecifierType {
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
