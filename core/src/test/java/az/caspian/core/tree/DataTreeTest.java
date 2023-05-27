package az.caspian.core.tree;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import org.junit.jupiter.api.Test;

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

}