package az.caspian.core.tree.node;

/**
 * A data node
 */
public class DataNode extends Node {
  private final String name;
  private final String selector;
  private String value;

  public DataNode(String name, String selector) {
    this.name = name;
    this.selector = selector;
  }

  public String getName() {
    return name;
  }

  public String getSelector() {
    return selector;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
