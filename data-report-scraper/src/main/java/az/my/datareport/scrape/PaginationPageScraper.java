package az.my.datareport.scrape;

import az.my.datareport.model.DataColumn;
import az.my.datareport.model.DataRow;
import az.my.datareport.tree.*;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PaginationPageScraper implements Scraper<Pagination> {

    public ReportDataTable scrape(Pagination pagination) {
        ReportDataTable reportDataTable = new ReportDataTable();

        var pageParameters = pagination.getPageParameters();
        for (int i = pageParameters.getMinPage(); i < pageParameters.getMaxPage(); i++) {
            String url = pageParameters.getPageUrl(i);

            WebPage page = new WebPage(url, true);
            page.connectWithDelay(pageParameters.getDelayBetweenPages());

            List<DataRow> dataRows = fetchWebElements(page, pagination.getRoot());
            reportDataTable.addAll(dataRows);
        }

        return reportDataTable;
    }

    private List<DataRow> fetchWebElements(WebPage page, DataTree<DataNode> root) {
        List<DataRow> dataRows = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(root.value().getSelector());

        for (WebElement webElement : webElements) {
            List<DataColumn> dataColumns = new ArrayList<>();

            for (DataTree<DataNode> node : root.nodes()) {
                String value = page.fetchElementAsText(node.value().getSelector(), webElement);
                var column = new DataColumn(node.value().getName(), value);
                dataColumns.add(column);
            }

            DataRow dataRow = new DataRow();
            dataRow.addColumns(dataColumns);
            dataRows.add(dataRow);
        }

        return dataRows;
    }
}
