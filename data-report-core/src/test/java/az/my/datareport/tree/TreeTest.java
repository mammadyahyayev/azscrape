package az.my.datareport.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeTest {

    @Test
    void testAddNode_whenNodesAdded_returnExactSizeOfTree() {
        Tree tree = new Tree();

        tree.addNode(new TempDataNode("First node"));
        tree.addNode(new TempDataNode("Second node"));

        assertEquals(2, tree.size());
    }

}