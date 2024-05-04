package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.node.*;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NodeExecutor {

  public void executeTimeoutNode(TimeoutNode timeoutNode) {
    try {
      Thread.sleep(timeoutNode.getTimeout());
    } catch (InterruptedException e) {
      // ignore interruption
    }
  }

  public void executeActionNode(ActionNode actionNode, WebElement webElement) {
    if (webElement == null) {
      return;
    }

    var elementToClick = webElement.findElement(By.cssSelector(actionNode.getSelector()));
    if (elementToClick == null) {
      return;
    }

    switch (actionNode.getActionType()) {
      case CLICK -> elementToClick.click();
      default -> throw new UnsupportedOperationException("Not supported yet.");
    }
  }

  public DataColumn executeDataNode(DataNode dataNode, WebElement webElement) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webElement, "WebElement can't be null");

    var htmlElement = new HtmlElement(webElement);
    var elementValue = htmlElement.getElement(dataNode.getSelector());

    return new DataColumn(dataNode.getName(), elementValue);
  }

  public @Nullable DataColumn executeDataNode(DataNode dataNode, WebPage webPage) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webPage, "WebPage can't be null");

    Optional<WebElement> webElement = webPage.fetchWebElement(dataNode.getSelector());

    return webElement.map(element -> new DataColumn(dataNode.getName(), element.getText()))
      .orElse(null);
  }

  public @Nullable DataColumn executeKeyValueNode(KeyValueNode keyValueNode, WebPage page) {
    String column = page.fetchElementAsText(keyValueNode.getKeySelector());
    String value = page.fetchElementAsText(keyValueNode.getValueSelector());

    if (StringUtils.isNullOrEmpty(column)) {
      return null;
    }

    return new DataColumn(column, value);
  }

  public @Nullable DataColumn executeKeyValueNode(KeyValueNode keyValueNode, WebElement element) {
    WebElement keyElement = element.findElement(By.cssSelector(keyValueNode.getKeySelector()));
    WebElement valueElement = element.findElement(By.cssSelector(keyValueNode.getValueSelector()));

    if (keyElement == null) {
      return null;
    }


    return new DataColumn(keyElement.getText(), valueElement != null ? valueElement.getText() : "");
  }

  public List<DataRow> executeListNode(ListNode listNode, WebPage webPage) {
    List<DataRow> dataRows = new ArrayList<>();
    List<WebElement> webElements = webPage.fetchWebElements(listNode.getSelector());

    for (WebElement webElement : webElements) {
      var dataRow = new DataRow();
      List<DataColumn> dataColumns = executeNodes(listNode.getChildren(), webElement);

      if (!dataColumns.isEmpty()) {
        dataRow.addColumns(dataColumns);
        dataRows.add(dataRow);
      }
    }

    return dataRows;
  }

  public DataRow executeParentNode(ParentNode parentNode, WebElement parentWebElement) {
    WebElement webElement = parentWebElement.findElement(By.cssSelector(parentNode.getSelector()));

    var dataRow = new DataRow();
    List<DataColumn> dataColumns = executeNodes(parentNode.getChildren(), webElement);
    dataRow.addColumns(dataColumns);
    return dataRow;
  }

  public DataRow executeParentNode(ParentNode parentNode, WebPage page) {
    Optional<WebElement> webElement = page.fetchWebElement(parentNode.getSelector());

    var dataRow = new DataRow();

    webElement.ifPresent(element -> {
      List<DataColumn> dataColumns = executeNodes(parentNode.getChildren(), element);
      dataRow.addColumns(dataColumns);
    });

    return dataRow;
  }

  public List<DataColumn> executeNodes(List<Node> nodes, WebElement webElement) {
    List<DataColumn> dataColumns = new ArrayList<>();

    for (Node node : nodes) {
      if (node instanceof DataNode dataNode) {
        dataColumns.add(executeDataNode(dataNode, webElement));
      } else if (node instanceof KeyValueNode keyValueNode) {
        dataColumns.add(executeKeyValueNode(keyValueNode, webElement));
      } else if (node instanceof TimeoutNode timeoutNode) {
        executeTimeoutNode(timeoutNode);
      } else if (node instanceof ActionNode actionNode) {
        executeActionNode(actionNode, webElement);
      }
    }

    return dataColumns;
  }

  public List<DataRow> executeNodes(List<Node> nodes, WebPage webPage) {
    List<DataRow> dataRows = new ArrayList<>();

    for (Node node : nodes) {
      if (node instanceof ListNode listNode) {
        dataRows.addAll(executeListNode(listNode, webPage));
      }
    }

    return dataRows;
  }
}
