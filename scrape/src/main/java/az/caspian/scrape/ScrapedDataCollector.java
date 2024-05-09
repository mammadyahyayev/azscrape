package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.node.*;
import az.caspian.core.utils.Asserts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class is used to collect already scraped data into internal models which represents Tree
 * architecture.
 */
public class ScrapedDataCollector {

  public DataRow collect(List<Node> nodes, WebPage page) {
    var row = new DataRow();
    List<DataColumn> columns = new ArrayList<>();
    for (Node node : nodes) {
      if (node instanceof DataNode dataNode) {
        Optional<DataColumn> dataColumn = collect(dataNode, page);
        dataColumn.ifPresent(columns::add);
      } else if (node instanceof KeyValueNode keyValueNode) {
        String column = page.fetchElementAsText(keyValueNode.getKeySelector());
        String value = page.fetchElementAsText(keyValueNode.getValueSelector());
        columns.add(new DataColumn(column, value));
      } else if (node instanceof ListNode listNode) {
        columns.addAll(collect(listNode, page));
      }
    }

    row.addColumns(columns);
    return row;
  }

  public List<DataRow> collectListNodes(List<Node> nodes, WebPage page) {
    ListNode listNode = (ListNode) nodes.get(0);

    List<DataRow> dataRows = new ArrayList<>();

    Optional<LinkNode> linkNode = listNode.getChildren().stream()
      .filter(Node::isLinkNode)
      .map(LinkNode.class::cast)
      .findFirst();

    while (true) {
      List<SafeWebElement> webElements = page.fetchWebElements(listNode.getSelector());
      for (var webElement : webElements) {
        DataRow dataRow = collect(listNode.getChildren(), webElement);
        dataRows.add(dataRow);
      }

      if (linkNode.isEmpty()) {
        break;
      }

      Optional<SafeWebElement> webElement = page.fetchWebElement(linkNode.get().getSelector());
      if (webElement.isEmpty()) {
        break;
      }

      webElement.get().click();

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        // ignore
      }
    }

    return dataRows;
  }

  public DataRow collect(List<Node> nodes, SafeWebElement element) {
    var row = new DataRow();
    List<DataColumn> columns = new ArrayList<>();
    for (Node node : nodes) {
      if (node instanceof DataNode dataNode) {
        Optional<DataColumn> dataColumn = collect(dataNode, element);
        dataColumn.ifPresent(columns::add);
      } else if (node instanceof KeyValueNode keyValueNode) {
        String column = element.getElementValue(keyValueNode.getKeySelector());
        String value = element.getElementValue(keyValueNode.getValueSelector());
        if (column == null) continue;
        columns.add(new DataColumn(column, value));
      } else if (node instanceof ActionNode actionNode) {
        Optional<SafeWebElement> webElement = element.findElement(actionNode.getSelector());
        if (webElement.isEmpty()) {
          continue;
        }

        if (actionNode.getActionType() == Action.CLICK) {
          element.click();
          try {
            Thread.sleep(4000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    }

    row.addColumns(columns);
    return row;
  }

  public Optional<DataColumn> collect(DataNode dataNode, WebPage page) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(page, "WebPage can't be null");

    Optional<SafeWebElement> webElement = page.fetchWebElement(dataNode.getSelector());
    return webElement.map(element -> new DataColumn(dataNode.getName(), element.getText()));
  }

  public Optional<DataColumn> collect(DataNode dataNode, SafeWebElement webElement) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webElement, "SafeWebElement can't be null");

    var element = webElement.getElementValue(dataNode.getSelector());
    if (element == null) return Optional.empty();
    return Optional.of(new DataColumn(dataNode.getName(), element));
  }

  public List<DataColumn> collect(ListNode listNode, WebPage page) {
    Asserts.notNull(listNode, "ListNode can't be null!");
    Asserts.notNull(page, "WebPage can't be null!");

    List<DataColumn> columns = new ArrayList<>();
    List<SafeWebElement> webElements = page.fetchWebElements(listNode.getSelector());
    webElements.forEach(
      webElement -> columns.addAll(collect(listNode.getChildren(), webElement).columns()));
    return columns;
  }
}
