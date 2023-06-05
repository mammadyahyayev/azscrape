package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaginationItemPageScraper extends AbstractScrapeTemplate<PaginationItemTemplate> {

    private ScrapeErrorCallback callback;

    public PaginationItemPageScraper() {
    }

    public PaginationItemPageScraper(ScrapeErrorCallback callback) {
        this.callback = callback;
    }

    public ReportDataTable scrape(PaginationItemTemplate template) {
        ReportDataTable reportDataTable = new ReportDataTable();

        String url = null;
        int current = 0;
        try (WebBrowser browser = new WebBrowser()) {
            browser.open();

            var pageParameters = template.getPageParameters();
            for (current = pageParameters.getMinPage(); current <= pageParameters.getMaxPage(); current++) {
                url = pageParameters.getPageUrl(current);

                WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

                DataTree<DataNode> root = template.getRoot();
                List<WebElement> elements = page.fetchWebElements(root.value().getSelector());

                for (WebElement element : elements) {
                    WebElement link = element.findElement(By.cssSelector(
                            root.nodes().stream()
                                    .map(DataTree::value)
                                    .filter(DataNode::isLink)
                                    .findFirst()
                                    .orElseThrow()
                                    .getSelector()
                    ));
                    if (isClickable(link)) {
                        String urlOfSubPage = link.getAttribute("href");

                        WebPage webPage = browser.goTo(urlOfSubPage);

                        List<DataRow> dataRows = fetchWebElements(webPage,
                                root.nodes().stream()
                                        .filter(node -> node.value().isRoot())
                                        .findFirst()
                                        .orElseThrow()
                        );

                        reportDataTable.addAll(dataRows);

                        browser.backToPrevPage();
                    }
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

    private boolean isClickable(WebElement element) {
        return Objects.equals(element.getAriaRole(), "link") ||
                Objects.equals(element.getTagName(), "a");
    }

    protected List<DataRow> fetchWebElements(WebPage page, DataTree<DataNode> root) {
        List<DataRow> dataRows = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(root.value().getSelector());

        for (WebElement webElement : webElements) {
            List<DataColumn> dataColumns = new ArrayList<>();

            boolean canOmit = false;
            for (DataTree<DataNode> node : root.nodes()) {
                String value = page.fetchElementsAsText(node.value().getSelector(), webElement);
                if (node.value().isKeyColumn()) {
                    canOmit = true;
                    break;
                }

                var column = new DataColumn(node.value().getName(), value);
                dataColumns.add(column);
            }

            if (!canOmit) {
                DataRow dataRow = new DataRow();
                dataRow.addColumns(dataColumns);
                dataRows.add(dataRow);
            }
        }

        return dataRows;
    }

}
