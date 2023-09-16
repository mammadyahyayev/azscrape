package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.KeyValueDataNode;
import az.caspian.core.tree.Node;
import az.caspian.core.tree.ParentNode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScrapedDataCollector {

    public Optional<DataColumn> collect(DataNode dataNode, WebPage page) {
        if (dataNode == null) {
            throw new IllegalArgumentException("DataNode can't be null!");
        }

        if (page == null) {
            throw new IllegalArgumentException("WebPage can't be null");
        }

        Optional<WebElement> webElement = page.fetchWebElement(dataNode.getSelector());
        return webElement.map(element -> new DataColumn(dataNode.getName(), element.getText()));
    }

    public Optional<DataColumn> collect(DataNode dataNode, WebElement webElement) {
        if (dataNode == null) {
            throw new IllegalArgumentException("DataNode can't be null!");
        }

        if (webElement == null) {
            throw new IllegalArgumentException("WebElement can't be null");
        }

        WebElement element = webElement.findElement(By.cssSelector(dataNode.getSelector()));
        if (element == null) return Optional.empty();

        return Optional.of(new DataColumn(dataNode.getName(), element.getText()));
    }

    // TODO: This method belongs to ListNode, in ParentNode we don't need to get all the elements
    //  ListNode is an array in json, however ParentNode is object in json
    // TODO: Consider to rename ParentNode to ObjectNode to make it easy to understand.
    public List<DataColumn> collect(ParentNode parentNode, WebPage page) {
        List<DataColumn> columns = new ArrayList<>();
        List<WebElement> webElements = page.fetchWebElements(parentNode.getSelector());
        for (Node child : parentNode.getChildren()) {
            for (WebElement webElement : webElements) {
                if (child.isDataNode()) {
                    WebElement element = webElement.findElement(By.cssSelector(child.getSelector()));
                    if (element == null) continue;
                    columns.add(new DataColumn(child.getName(), element.getText()));
                } else if (child.isKeyValueNode()) {
                    KeyValueDataNode keyValueNode = (KeyValueDataNode) child;
                    String column = page.fetchElementAsText(keyValueNode.getKey(), webElement);
                    String value = page.fetchElementAsText(keyValueNode.getValue(), webElement);
                    columns.add(new DataColumn(column, value));
                }
            }
        }

        return columns;
    }


}
