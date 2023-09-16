package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.*;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PaginationItemVisitorScraper extends AbstractScrapeTemplate<PaginationItemVisitorTemplate> {

    private ScrapeErrorCallback callback;
    private ScrapedDataCollector collector;

    public PaginationItemVisitorScraper() {
        collector = new ScrapedDataCollector();
    }

    public PaginationItemVisitorScraper(ScrapeErrorCallback callback) {
        this.callback = callback;
    }

    public ReportDataTable scrape(PaginationItemVisitorTemplate template) {
        ReportDataTable reportDataTable = new ReportDataTable();

        String url = null;
        int current = 0;
        try (WebBrowser browser = new WebBrowser()) {
            browser.open();

            var pageParameters = template.getPageParameters();
            for (current = pageParameters.getMinPage(); current <= pageParameters.getMaxPage(); current++) {
                url = pageParameters.getPageUrl(current);

                WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

                DataTree<Node> tree = template.getTree();
                List<WebElement> elements = page.fetchWebElements(tree.getRoot().getSelector());
                for (WebElement element : elements) {
                    List<DataRow> dataRows = new ArrayList<>();
                    String urlOfSubPage = element.getAttribute("href");
                    WebPage webPage = browser.goTo(urlOfSubPage, pageParameters.getDelayBetweenPages());

                    DataRow dataRow = collectPageData(tree, webPage);
                    dataRows.add(dataRow);

                    browser.backToPrevPage();

                    reportDataTable.addAll(dataRows);
                }
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

    private DataRow collectPageData(DataTree<Node> tree, WebPage page) {
        List<DataColumn> dataColumns = new ArrayList<>();

        for (Node node : tree.nodes()) {
            if (node.isDataNode()) {
                DataNode dataNode = (DataNode) node;
                collector.collect(dataNode, page).ifPresent(dataColumns::add);
            } else if (node.isParentNode()) {
                ParentNode parentNode = (ParentNode) node;
                List<DataColumn> columns = collector.collect(parentNode, page);
                dataColumns.addAll(columns);
            } else if (node.isListNode()) {
                ListNode listNode = (ListNode) node;
                List<DataColumn> columns = collector.collect(listNode, page);
                dataColumns.addAll(columns);
            }
        }

        dataColumns.add(new DataColumn("link", page.getUrl()));

        var dataRow = new DataRow();
        dataRow.addColumns(dataColumns);

        return dataRow;
    }
}
