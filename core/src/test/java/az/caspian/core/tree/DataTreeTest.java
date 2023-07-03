package az.caspian.core.tree;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DataTreeTest {

    @Test
    void testAddChildNode() {
        var root = new DataTree<>(new DataNode("Parent", ".repo-list-item"));
        var titleNode = new DataTree<>(new DataNode("title", ".v-align-middle"));
        var descNode = new DataTree<>(new DataNode("description", ".mb-1"));

        root.addSubNode(titleNode);
        root.addSubNode(descNode);

        assertNull(root.parent());
        assertEquals(root, titleNode.parent());
        assertEquals(root, descNode.parent());

        assertEquals(2, root.nodes().size());
        assertThrows(UnsupportedOperationException.class, () -> root.nodes().add(new DataTree<>(null)));
    }

    @Test
    void testAddChildNodeWithChildren() {
        var root = new DataTree<>(new DataNode("Parent", ".repo-list-item"));
        var titleNode = new DataTree<>(new DataNode("title", ".v-align-middle"));
        var descNode = new DataTree<>(new DataNode("description", ".mb-1"));

        var titleChildNode = new DataTree<>(new DataNode("title-child", ".title-child"));
        titleNode.addSubNode(titleChildNode);

        root.addSubNode(titleNode);
        root.addSubNode(descNode);


        assertNull(root.parent());
        assertEquals(root, titleNode.parent());
        assertEquals(root, descNode.parent());

        assertEquals(titleNode, titleChildNode.parent());
        assertEquals(1, titleNode.nodes().size());

        assertEquals(2, root.nodes().size());
        assertThrows(UnsupportedOperationException.class, () -> root.nodes().add(new DataTree<>(null)));
    }

    @Test
    void testNodeIsRoot() {
        var root = new DataTree<>(new DataNode("Parent", ".repo-list-item"));
        var titleNode = new DataTree<>(new DataNode("title", ".v-align-middle"));
        var descNode = new DataTree<>(new DataNode("description", ".mb-1"));

        var titleChildNode = new DataTree<>(new DataNode("title-child", ".title-child"));
        titleNode.addSubNode(titleChildNode);

        assertTrue(descNode.isRoot());
        assertTrue(titleNode.isRoot());

        root.addSubNode(titleNode);
        root.addSubNode(descNode);

        assertFalse(titleNode.isRoot());
        assertFalse(descNode.isRoot());
        assertFalse(titleChildNode.isRoot());

        assertTrue(root.isRoot());
    }


    @Test
    void testNodeValue() {
        var node = new DataNode("Parent", ".repo-list-item");
        var root = new DataTree<>(node);

        assertEquals(node, root.value());
        assertEquals(node.getName(), root.value().getName());
        assertEquals(node.getSelector(), root.value().getSelector());
    }

    @Test
    void testRootNodeHasChildren() {
        var root = new DataNode("test", "test");

        var tree = new DataTree<>(root);

        var child1 = new DataNode("child 1", "child 1");
        var child2 = new DataNode("child 2", "child 2");
        var child3 = new DataNode("child 3", "child 3");

        var subChild1 = new DataNode("sub child 1", "sub child 1");
        var subChild2 = new DataNode("sub child 2", "sub child 2");

        tree.addChild(child1, root);
        tree.addChild(child2, root);
        tree.addChild(child3, root);

        tree.addChild(subChild1, child1);
        tree.addChild(subChild2, child1);

        assertThat(tree.getChildren(root)).hasSize(3);
        assertThat(tree.getChildren(child1)).hasSize(2);
        assertThat(tree.getChildren(child2)).hasSize(0);

        assertThat(tree.getChildren(root)).contains(child2, child1, child3);
    }
}