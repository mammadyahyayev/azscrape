package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PageParameters {
    private String pageUrl;
    private final List<QueryParameter> queryParameters;
    private int minPage;
    private int maxPage;
    private int delayBetweenPages;

    public PageParameters() {
        this.queryParameters = new ArrayList<>();
    }

    public void addQueryParameter(QueryParameter queryParameter) {
        Asserts.required(queryParameter, "queryParam cannot be null!");

        queryParameters.add(queryParameter);
    }

    //region Getters & Setters

    public String getPageUrl() {
        return pageUrl;
    }

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

    public String buildPageUrl(int pageNumber) {
        String url = this.pageUrl;
        if (queryParameters.size() == 0) {
            char end = url.charAt(url.length() - 1);
            if (end == '/') {
                return url + pageNumber;
            }

            return url + "/" + pageNumber;
        }

        String queryParam = queryParameters.stream().map(param -> {
                    if (param.isPageParameter()) {
                        return param.getKey() + "=" + pageNumber;
                    }
                    return param.getKey() + "=" + param.getValue();
                })
                .collect(Collectors.joining("&"));
        url += "?" + queryParam;

        return url;
    }

}
