package az.caspian.core.tree.node;

/**
 * The node functionality is similar to {@link DataNode} with only difference is
 * {@link AttributeNode} is used to get attribute value of HTML element on the web page.
 * <pre>
 * {@code <div name='AZSCRAPE'>Test</div>}
 * In above example, <b>name</b> is an attribute, the value of that attribute is <b>AZSCRAPE</b>
 * </pre>
 */
public class AttributeNode extends Node {
  private final String name;
  private final String attributeName;
  private final String selector;

  public AttributeNode(String name, String attributeName, String selector) {
    this.name = name;
    this.attributeName = attributeName;
    this.selector = selector;
  }

  /**
   * Creates instance of {@link AttributeNode} by assigning
   * {@link AttributeNode#attributeName} field's value
   * to {@link AttributeNode#name} field
   *
   * @param attributeName attribute name of {@link AttributeNode}
   * @param selector      selector of {@link AttributeNode}
   */
  public AttributeNode(String attributeName, String selector) {
    this.name = attributeName;
    this.attributeName = attributeName;
    this.selector = selector;
  }

  public String getName() {
    return name;
  }

  public String getAttributeName() {
    return attributeName;
  }

  public String getSelector() {
    return selector;
  }
}
