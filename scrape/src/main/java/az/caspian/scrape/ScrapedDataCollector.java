package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.*;
import az.caspian.core.utils.Asserts;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.WebElement;

/**
 * The class is used to collect already scraped data into internal models which represents Tree
 * architecture.
 */
public class ScrapedDataCollector {

  public DataRow collect(List<Node> nodes, WebPage page) {
    var row = new DataRow();
    List<DataColumn> columns = new ArrayList<>();
    for (Node node : nodes) {
      if (node.isDataNode()) {
        var dataNode = (DataNode) node;
        Optional<DataColumn> dataColumn = collect(dataNode, page);
        dataColumn.ifPresent(columns::add);
      } else if (node.isKeyValueNode()) {
        var keyValueNode = (KeyValueDataNode) node;
        String column = page.fetchElementAsText(keyValueNode.getKeySelector());
        String value = page.fetchElementAsText(keyValueNode.getValueSelector());
        columns.add(new DataColumn(column, value));
      } else if (node.isListNode()) {
        var listNode = (ListNode) node;
        columns.addAll(collect(listNode, page));
      }
    }

    row.addColumns(columns);
    return row;
  }

  public DataRow collect(List<Node> nodes, WebElement element) {
    var htmlElement = new HtmlElement(element);

    var row = new DataRow();
    List<DataColumn> columns = new ArrayList<>();
    for (Node node : nodes) {
      if (node.isDataNode()) {
        var dataNode = (DataNode) node;
        Optional<DataColumn> dataColumn = collect(dataNode, element);
        dataColumn.ifPresent(columns::add);
      } else if (node.isKeyValueNode()) {
        var keyValueNode = (KeyValueDataNode) node;
        String column = htmlElement.getElement(keyValueNode.getKeySelector());
        String value = htmlElement.getElement(keyValueNode.getValueSelector());
        if (column == null) continue;
        columns.add(new DataColumn(column, value));
      }
    }

    row.addColumns(columns);
    return row;
  }

  public Optional<DataColumn> collect(DataNode dataNode, WebPage page) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(page, "WebPage can't be null");

    Optional<WebElement> webElement = page.fetchWebElement(dataNode.getSelector());
    return webElement.map(element -> new DataColumn(dataNode.getName(), element.getText()));
  }

  public Optional<DataColumn> collect(DataNode dataNode, WebElement webElement) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webElement, "WebElement can't be null");

    var htmlElement = new HtmlElement(webElement);

    var element = htmlElement.getElement(dataNode.getSelector());
    if (element == null) return Optional.empty();
    return Optional.of(new DataColumn(dataNode.getName(), element));
  }

  public List<DataColumn> collect(ListNode listNode, WebPage page) {
    Asserts.notNull(listNode, "ListNode can't be null!");
    Asserts.notNull(page, "WebPage can't be null!");

    List<DataColumn> columns = new ArrayList<>();
    List<WebElement> webElements = page.fetchWebElements(listNode.getSelector());
    webElements.forEach(
        webElement -> columns.addAll(collect(listNode.getChildren(), webElement).columns()));
    return columns;
  }
}
