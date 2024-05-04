package az.caspian.core.tree;

import az.caspian.core.tree.node.DataNode;
import az.caspian.core.tree.node.KeyValueDataNode;
import az.caspian.core.tree.node.ParentNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParentNodeTest {

  @Test
  void testAddChildrenIntoParentNode() {
    ParentNode parentNode = new ParentNode("parent-node", ".parent");

    DataNode child1 = new DataNode("child-node-1", ".child-node-1");
    DataNode child2 = new DataNode("child-node-2", ".child-node-2");
    KeyValueDataNode keyValueNode = new KeyValueDataNode(".key", ".value");

    parentNode.addChild(child1);
    parentNode.addChild(child2);
    parentNode.addChild(keyValueNode);

    assertEquals(parentNode.getChildren().size(), 3);
    assertTrue(
        parentNode.getChildren().stream().anyMatch(child -> child instanceof KeyValueDataNode));
  }
}
