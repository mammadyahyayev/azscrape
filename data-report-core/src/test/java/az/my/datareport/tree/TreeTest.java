package az.my.datareport.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeTest {

    @Test
    void testAddNode_whenNodesAdded_returnExactSizeOfTree() {
        Tree tree = new Tree();

        tree.addNode(new TempDataNode("First node"));
        tree.addNode(new TempDataNode("Second node"));

        assertEquals(2, tree.size());
    }

    @Test
    void testNodes_whenModifyingReturnedList_throwException() {
        Tree tree = new Tree();

        tree.addNode(new TempDataNode("First node"));
        tree.addNode(new TempDataNode("Second node"));

        List<TempDataNode> nodes = tree.nodes();

        assertThrows(UnsupportedOperationException.class, () -> nodes.add(new TempDataNode("Third node")));
    }

    //TODO: Change this method after implementation of location, order, level
    @Test
    void testGetFirst() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode("First node");
        TempDataNode second = new TempDataNode("Second node");

        tree.addNode(first);
        tree.addNode(second);

        assertEquals(first, tree.getFirst());
    }

    //TODO: Change this method after implementation of location, order, level
    @Test
    void testGetLast() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode("First node");
        TempDataNode second = new TempDataNode("Second node");

        tree.addNode(first);
        tree.addNode(second);

        assertEquals(second, tree.getLast());
    }

}