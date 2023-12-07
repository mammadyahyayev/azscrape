package az.caspian.core.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DataTree<N extends Node> implements Tree<N> {
  private final List<N> nodes = new ArrayList<>();

  public DataTree() {}

  @Override
  public List<N> nodes() {
    return Collections.unmodifiableList(nodes);
  }

  public Optional<N> findNode(NodeType nodeType) {
    if (nodes.isEmpty()) {
      return Optional.empty();
    }

    if (nodeType == NodeType.DATA) {
      return this.nodes.stream().filter(Node::isDataNode).findFirst();
    } else if (nodeType == NodeType.LINK) {
      return this.nodes.stream().filter(Node::isLinkNode).findFirst();
    } else if (nodeType == NodeType.PARENT) {
      return this.nodes.stream().filter(Node::isParentNode).findFirst();
    } else if (nodeType == NodeType.KEY_VALUE) {
      return this.nodes.stream().filter(Node::isKeyValueNode).findFirst();
    } else if (nodeType == NodeType.LIST) {
      return this.nodes.stream().filter(Node::isListNode).findFirst();
    }

    return Optional.empty();
  }

  @Override
  public void addNode(N node) {
    if (node == null) {
      throw new IllegalArgumentException("Node can't be add DataTree as null!");
    }

    nodes.add(node);
  }
}
