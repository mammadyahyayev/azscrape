package az.my.datareport.scrape;

import az.my.datareport.model.Column;
import az.my.datareport.model.Row;
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

            List<Row> rows = fetchWebElements(page, pagination.getRoot());
            reportDataTable.addAll(rows);
        }

        return reportDataTable;
    }

    private List<Row> fetchWebElements(WebPage page, DataTree<DataNode> root) {
        List<Row> rows = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(root.value().getSelector());

        for (WebElement webElement : webElements) {
            List<Column> columns = new ArrayList<>();

            for (DataTree<DataNode> node : root.nodes()) {
                String value = page.fetchElementAsText(node.value().getSelector(), webElement);
                var column = new Column(node.value().getName(), value);
                columns.add(column);
            }

            Row row = new Row();
            row.addColumns(columns);
            rows.add(row);
        }

        return rows;
    }
}
