package az.my.datareport.tree;

public class PageParameters {
    private String pageUrl;
    private String queryParam;
    private int minPage;
    private int maxPage;
    private int delayBetweenPages;

    //region Getters & Setters

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
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

    public void setDelayBetweenPages(int delayBetweenPages) {
        this.delayBetweenPages = delayBetweenPages;
    }

//endregion
}
