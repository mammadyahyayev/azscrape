package az.caspian.scrape.templates.pagination;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.core.utils.StringUtils;
import az.caspian.scrape.Scraper;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PaginationPageScraper implements Scraper<PaginationTemplate> {

    private ScrapeErrorCallback callback;

    public PaginationPageScraper() {}

    public PaginationPageScraper(ScrapeErrorCallback callback) {
        this.callback = callback;
    }

    public ReportDataTable scrape(PaginationTemplate paginationTemplate) {
        ReportDataTable reportDataTable = new ReportDataTable();

        try (WebBrowser browser = new WebBrowser()) {
            browser.open();

            var pageParameters = paginationTemplate.getPageParameters();
            for (int i = pageParameters.getMinPage(); i <= pageParameters.getMaxPage(); i++) {
                String url = pageParameters.getPageUrl(i);

                WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

                List<DataRow> dataRows = fetchWebElements(page, paginationTemplate.getRoot());
                reportDataTable.addAll(dataRows);
            }
        } catch (Exception e) {
            callback.handle(e.getMessage(), reportDataTable);
            throw new RuntimeException("Failed to scrape data from Web page: " + e.getMessage(), e);
        }

        return reportDataTable;
    }

    private List<DataRow> fetchWebElements(WebPage page, DataTree<DataNode> root) {
        List<DataRow> dataRows = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(root.value().getSelector());

        for (WebElement webElement : webElements) {
            List<DataColumn> dataColumns = new ArrayList<>();

            boolean canOmit = false;
            for (DataTree<DataNode> node : root.nodes()) {
                String value = page.fetchElementAsText(node.value().getSelector(), webElement);
                if (node.value().isKeyColumn() && StringUtils.isNullOrEmpty(value)) {
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
