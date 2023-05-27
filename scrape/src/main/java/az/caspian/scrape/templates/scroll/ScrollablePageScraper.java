package az.caspian.scrape.templates.scroll;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;

import java.util.List;

public class ScrollablePageScraper extends AbstractScrapeTemplate<ScrollablePageTemplate> {

    public ReportDataTable scrape(ScrollablePageTemplate scrollablePageTemplate) {
        ReportDataTable reportDataTable = new ReportDataTable();

        ScrollablePageParameters pageParameters = scrollablePageTemplate.getPageParameters();

        try (WebBrowser browser = new WebBrowser()) {
            browser.open();

            WebPage webPage = browser.goTo(pageParameters.getUrl());

            long previousHeight = 0;
            long currentHeight = webPage.height();

            while (currentHeight != previousHeight) {
                List<DataRow> dataRows = fetchWebElements(webPage, scrollablePageTemplate.getRoot());
                reportDataTable.addAll(dataRows);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                webPage.scrollToEnd();
                previousHeight = currentHeight;
                currentHeight = webPage.height();
            }
        }

        return reportDataTable;
    }
}
