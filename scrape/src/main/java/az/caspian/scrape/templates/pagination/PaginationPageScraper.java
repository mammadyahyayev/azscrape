package az.caspian.scrape.templates.pagination;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;

import java.text.MessageFormat;
import java.util.List;

public class PaginationPageScraper extends AbstractScrapeTemplate<PaginationTemplate> {

    private ScrapeErrorCallback callback;

    public PaginationPageScraper() {
    }

    public PaginationPageScraper(ScrapeErrorCallback callback) {
        this.callback = callback;
    }

    public ReportDataTable scrape(PaginationTemplate paginationTemplate) {
        ReportDataTable reportDataTable = new ReportDataTable();

        String url = null;
        int current = 0;
        try (WebBrowser browser = new WebBrowser()) {
            browser.open();

            var pageParameters = paginationTemplate.getPageParameters();
            for (current = pageParameters.getMinPage(); current <= pageParameters.getMaxPage(); current++) {
                url = pageParameters.getPageUrl(current);

                WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

                List<DataRow> dataRows = fetchWebElements(page, paginationTemplate.getTree());
                reportDataTable.addAll(dataRows);
            }
        } catch (Exception e) {
            String message = MessageFormat.format(
                    "Failed to scrape data from {0} in page {1}, Exception: {2}", url, current, e.getMessage()
            );

            if (callback != null)
                callback.handle(message, reportDataTable);

            throw new RuntimeException(message, e);
        }

        return reportDataTable;
    }
}
