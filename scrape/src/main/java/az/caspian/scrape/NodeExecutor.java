package az.caspian.scrape;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.node.*;
import az.caspian.core.utils.Asserts;
import az.caspian.core.utils.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class NodeExecutor {

  private static final Logger LOG = LoggerFactory.getLogger(NodeExecutor.class);

  public void executeTimeoutNode(TimeoutNode timeoutNode) {
    try {
      Thread.sleep(timeoutNode.getTimeout());
      LOG.debug("TimeoutNode is executed and waiting {} seconds!", timeoutNode.getTimeoutInSeconds());
    } catch (InterruptedException e) {
      LOG.error("TimeoutNode interrupted!");
    }
  }

  public void executeActionNode(ActionNode actionNode, SafeWebElement webElement) {
    if (webElement == null) {
      return;
    }

    var elementForAction = webElement.findElement(actionNode.getSelector());
    if (elementForAction.isEmpty()) {
      LOG.debug("Element with selector [{}] not found to apply {}",
        actionNode.getSelector(), actionNode.getActionType());
      return;
    }

    if (Objects.requireNonNull(actionNode.getActionType()) == Action.CLICK) {
      elementForAction.get().click();
      LOG.debug("Element with selector [{}] clicked", actionNode.getSelector());
    } else {
      String message = "Unsupported action type " + actionNode.getActionType();
      LOG.error(message);
      throw new UnsupportedOperationException(message);
    }
  }

  public void executeInterventionNode(InterventionNode interventionNode) {
    if (interventionNode == null) {
      LOG.warn("InterventionNode is null and do nothing!");
      return;
    }

    var notifier = new InterventionNotifier(interventionNode);
    notifier.notifyClients();
  }

  public DataColumn executeDataNode(DataNode dataNode, SafeWebElement webElement) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webElement, "WebElement can't be null");

    var elementValue = webElement.getElementValue(dataNode.getSelector());
    LOG.debug("DataNode is executed and got {} for {}", elementValue, dataNode.getName());
    return new DataColumn(dataNode.getName(), elementValue);
  }

  private DataColumn executeAttributeNode(AttributeNode attributeNode, SafeWebElement webElement) {
    Asserts.notNull(attributeNode, "AttributeNode can't be null!");
    Asserts.notNull(webElement, "WebElement can't be null");

    DataColumn dataColumn = webElement.findElement(attributeNode.getSelector())
      .map(element -> new DataColumn(attributeNode.getName(), element.getAttribute(attributeNode.getAttributeName())))
      .orElse(new DataColumn(attributeNode.getName(), ""));

    LOG.debug("AttributeNode is executed and got {} for {}", dataColumn.value(), dataColumn.name());
    return dataColumn;
  }

  public @Nullable DataColumn executeDataNode(DataNode dataNode, WebPage webPage) {
    Asserts.notNull(dataNode, "DataNode can't be null!");
    Asserts.notNull(webPage, "WebPage can't be null");

    Optional<SafeWebElement> webElement = webPage.fetchWebElement(dataNode.getSelector());

    return webElement.map(element -> new DataColumn(dataNode.getName(), element.getText())).orElse(null);
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

    DataColumn dataColumn = keyElement.map(safeWebElement -> {
      String value = valueElement.map(SafeWebElement::getText).orElse("");
      return new DataColumn(safeWebElement.getText(), value);
    }).orElse(null);

    if (dataColumn == null) {
      LOG.debug("Key element with [{}] selector is null for KeyValueNode", keyValueNode.getKeySelector());
      return null;
    }

    LOG.debug("KeyValueNode is executed and got {} for {}", dataColumn.value(), dataColumn.name());
    return dataColumn;
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

    LOG.debug("ListNode with [{}] selector is executed and got {} elements", listNode.getSelector(), dataRows.size());

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

    LOG.debug("ParentNode ({}) is executed and got {} columns in one row", parentNode.getSelector(),
      dataRow.columns().size());

    return dataRow;
  }

  public List<DataColumn> executeNodes(List<Node> nodes, SafeWebElement webElement) {
    List<DataColumn> dataColumns = new ArrayList<>();

    for (Node node : nodes) {
      if (node instanceof DataNode dataNode) {
        dataColumns.add(executeDataNode(dataNode, webElement));
      } else if (node instanceof AttributeNode attributeNode) {
        dataColumns.add(executeAttributeNode(attributeNode, webElement));
      } else if (node instanceof KeyValueNode keyValueNode) {
        dataColumns.add(executeKeyValueNode(keyValueNode, webElement));
      } else if (node instanceof TimeoutNode timeoutNode) {
        executeTimeoutNode(timeoutNode);
      } else if (node instanceof ActionNode actionNode) {
        executeActionNode(actionNode, webElement);
      } else if (node instanceof InterventionNode interventionNode) {
        executeInterventionNode(interventionNode);
      } else {
        throw new IllegalStateException("Not implemented node " + node);
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
