package az.caspian.core.tree;

import java.util.List;

/**
 * A Tree
 *
 * @param <N> A node
 */
public interface Tree<N> {
  enum NodeType {
    DATA, PARENT, KEY_VALUE, LINK, LIST
  }

  void addNode(N node);

  List<N> nodes();
}
