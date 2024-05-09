package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.node.*;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NodeExecutor {

  public void executeTimeoutNode(TimeoutNode timeoutNode) {
    try {
      Thread.sleep(timeoutNode.getTimeout());
    } catch (InterruptedException e) {
      // ignore interruption
    }
  }

  public void executeActionNode(ActionNode actionNode, SafeWebElement webElement) {
    if (webElement == null) {
      return;
    }

    var elementToClick = webElement.findElement(actionNode.getSelector());
    if (elementToClick.isEmpty()) {
      return;
    }

    if (Objects.requireNonNull(actionNode.getActionType()) == Action.CLICK) {
      elementToClick.get().click();
    } else {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }

  public DataColumn executeDataNode(DataNode dataNode, SafeWebElement webElement) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webElement, "WebElement can't be null");

    var elementValue = webElement.getElementValue(dataNode.getSelector());
    return new DataColumn(dataNode.getName(), elementValue);
  }

  public @Nullable DataColumn executeDataNode(DataNode dataNode, WebPage webPage) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webPage, "WebPage can't be null");

    Optional<SafeWebElement> webElement = webPage.fetchWebElement(dataNode.getSelector());

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

  public @Nullable DataColumn executeKeyValueNode(KeyValueNode keyValueNode, SafeWebElement element) {
    Optional<SafeWebElement> keyElement = element.findElement(keyValueNode.getKeySelector());
    Optional<SafeWebElement> valueElement = element.findElement(keyValueNode.getValueSelector());

    return keyElement.map(safeWebElement -> {
        String value = valueElement.map(SafeWebElement::getText).orElse("");
        return new DataColumn(safeWebElement.getText(), value);
      })
      .orElse(null);
  }

  public List<DataRow> executeListNode(ListNode listNode, WebPage webPage) {
    List<DataRow> dataRows = new ArrayList<>();
    List<SafeWebElement> webElements = webPage.fetchWebElements(listNode.getSelector());

    for (SafeWebElement webElement : webElements) {
      var dataRow = new DataRow();
      List<DataColumn> dataColumns = executeNodes(listNode.getChildren(), webElement);

      if (!dataColumns.isEmpty()) {
        dataRow.addColumns(dataColumns);
        dataRows.add(dataRow);
      }
    }

    return dataRows;
  }

  public DataRow executeParentNode(ParentNode parentNode, SafeWebElement parentWebElement) {
    Optional<SafeWebElement> webElement = parentWebElement.findElement(parentNode.getSelector());
    var dataRow = new DataRow();

    webElement.ifPresent(element -> {
      List<DataColumn> dataColumns = executeNodes(parentNode.getChildren(), element);
      dataRow.addColumns(dataColumns);
    });

    return dataRow;
  }

  public DataRow executeParentNode(ParentNode parentNode, WebPage page) {
    Optional<SafeWebElement> webElement = page.fetchWebElement(parentNode.getSelector());

    var dataRow = new DataRow();

    webElement.ifPresent(element -> {
      List<DataColumn> dataColumns = executeNodes(parentNode.getChildren(), element);
      dataRow.addColumns(dataColumns);
    });

    return dataRow;
  }

  public List<DataColumn> executeNodes(List<Node> nodes, SafeWebElement webElement) {
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
