package az.my.datareport.scrape.templates.pagination;

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

public class PaginationPageScraper implements Scraper<PaginationTemplate> {

    public ReportDataTable scrape(PaginationTemplate paginationTemplate) {
        ReportDataTable reportDataTable = new ReportDataTable();

        var pageParameters = paginationTemplate.getPageParameters();
        for (int i = pageParameters.getMinPage(); i <= pageParameters.getMaxPage(); i++) {
            String url = pageParameters.getPageUrl(i);

            WebPage page = new WebPage(url, true);
            page.connectWithDelay(pageParameters.getDelayBetweenPages());

            List<DataRow> dataRows = fetchWebElements(page, paginationTemplate.getRoot());
            reportDataTable.addAll(dataRows);
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
