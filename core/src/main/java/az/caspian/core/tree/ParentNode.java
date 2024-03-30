package az.caspian.core.tree;

import az.caspian.core.utils.Asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentNode extends Node {
  private final String name;
  private final String selector;
  private final List<Node> children = new ArrayList<>();

  public ParentNode(String name, String selector) {
    this.name = name;
    this.selector = selector;
  }

  public List<Node> getChildren() {
    return Collections.unmodifiableList(children);
  }

  public <T extends Node> void addChild(T node) {
    Asserts.notNull(node, "Child node cannot be null!");
    this.children.add(node);
  }

  public String getName() {
    return name;
  }

  public String getSelector() {
    return selector;
  }
}
