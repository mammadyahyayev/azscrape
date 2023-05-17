package az.my.datareport.scrape.templates.scroll;

import az.my.datareport.model.DataColumn;
import az.my.datareport.model.DataRow;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.scrape.WebPage;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataTree;
import az.my.datareport.tree.ReportDataTable;
import az.my.datareport.utils.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ScrollablePageScraper implements Scraper<ScrollablePageTemplate> {

    public ReportDataTable scrape(ScrollablePageTemplate scrollablePageTemplate) {
        ReportDataTable reportDataTable = new ReportDataTable();

        ScrollablePageParameters pageParameters = scrollablePageTemplate.getPageParameters();

        WebPage webPage = new WebPage(pageParameters.getUrl(), true);
        webPage.connect();

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
