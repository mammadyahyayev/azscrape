package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.*;
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

    public PaginationItemVisitorScraper() {
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
        List<Node> children = tree.getChildren(tree.getRoot());

        for (Node node : children) {
            if (node instanceof DataNode) {
                page.fetchWebElement(node.getSelector())
                        .ifPresent(element -> dataColumns.add(new DataColumn(node.getName(), element.getText())));
            } else if (node instanceof KeyValueDataNode) {
                KeyValueDataNode keyValueNode = (KeyValueDataNode) node;
                List<WebElement> keyValuePairs = page.fetchWebElements(keyValueNode.getParent());
                keyValuePairs.forEach(kv -> {
                    String column = page.fetchElementAsText(keyValueNode.getKey(), kv);
                    String value = page.fetchElementAsText(keyValueNode.getValue(), kv);
                    dataColumns.add(new DataColumn(column, value));
                });
            }
        }

        dataColumns.add(new DataColumn("link", page.getUrl()));

        var dataRow = new DataRow();
        dataRow.addColumns(dataColumns);

        return dataRow;
    }
}
