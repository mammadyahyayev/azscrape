package az.my.datareport.scrape;

import az.my.datareport.model.Column;
import az.my.datareport.model.Row;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.PaginationTree;
import az.my.datareport.tree.ReportDataTable;
import az.my.datareport.tree.Tree;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PaginationPageScraper implements Scraper {

    public ReportDataTable scrape(Tree tree) {
        if (!(tree instanceof PaginationTree)) {
            throw new IllegalArgumentException("tree type must be PaginationTree");
        }

        PaginationTree paginationTree = (PaginationTree) tree;
        ReportDataTable reportDataTable = new ReportDataTable();

        var pageParameters = paginationTree.getPageParameters();
        for (int i = pageParameters.getMinPage(); i < pageParameters.getMaxPage(); i++) {
            String url = pageParameters.getPageUrl() + "&p=" + i; //TODO: Change this

            WebPage page = new WebPage(url, true);
            page.connectWithDelay(pageParameters.getDelayBetweenPages());

            List<Row> rows = fetchWebElements(page, paginationTree.nodes());
            reportDataTable.addAll(rows);
        }

        return reportDataTable;
    }

    private List<Row> fetchWebElements(WebPage page, List<DataNode> nodes) {
        List<Row> rows = new ArrayList<>();

        for (DataNode node : nodes) {
            List<WebElement> webElements = page.fetchWebElements(node.getAttributeSelector());

            if (!node.hasSubNode()) {
                continue;
            }

            for (WebElement element : webElements) {
                List<Column> columns = new ArrayList<>();

                for (DataNode subNode : node.subNodes()) {
                    String value = page.fetchElementAsText(subNode.getAttributeSelector(), element);
                    var column = new Column(subNode.getAttributeName(), value);
                    columns.add(column);
                }

                Row row = new Row();
                row.addColumns(columns);
                rows.add(row);
            }
        }

        return rows;
    }
}
