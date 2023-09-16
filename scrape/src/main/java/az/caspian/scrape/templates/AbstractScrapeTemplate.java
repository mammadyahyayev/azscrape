package az.caspian.scrape.templates;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.scrape.WebPage;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScrapeTemplate<T extends ScrapeTemplate> implements Scraper<T> {

    protected List<DataRow> fetchWebElements(WebPage page, DataTree<Node> tree) {
        List<DataRow> dataRows = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(tree.getRoot().getSelector());

        for (WebElement webElement : webElements) {
            List<DataColumn> dataColumns = new ArrayList<>();

            for (Node node : tree.nodes()) {
                if (node.isDataNode()) {
                    String value = page.fetchElementAsText(node.getSelector(), webElement);
                    var column = new DataColumn(node.getName(), value);
                    dataColumns.add(column);
                }
            }

            DataRow dataRow = new DataRow();
            dataRow.addColumns(dataColumns);
            dataRows.add(dataRow);
        }

        return dataRows;
    }

}
