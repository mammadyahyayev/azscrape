package az.caspian.scrape.templates;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.core.utils.StringUtils;
import az.caspian.scrape.WebPage;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScrapeTemplate<T extends ScrapeTemplate> implements Scraper<T> {

    protected List<DataRow> fetchWebElements(WebPage page, DataTree<DataNode> root) {
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
