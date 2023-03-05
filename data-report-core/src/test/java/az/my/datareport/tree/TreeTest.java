package az.my.datareport.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testAddNode_whenNodeAddedFirstTime_returnInitialLocation() {
        Tree tree = new Tree();
        TempDataNode node = new TempDataNode("First node");
        tree.addNode(node);

        DataNodeLocation initialLocation = new DataNodeLocation("A", 0);
        assertEquals(initialLocation, node.getLocation());
        assertTrue(node.isRoot());
    }

    @Test
    void testAddNode_whenNodesAdded_returnTheirLocations() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode("First node");
        TempDataNode second = new TempDataNode("Second node");
        TempDataNode third = new TempDataNode("Third node");
        tree.addNode(first);
        tree.addNode(second);
        tree.addNode(third);

        DataNodeLocation firstLocation = new DataNodeLocation("A", 0);
        DataNodeLocation secondLocation = new DataNodeLocation("A", 1);
        DataNodeLocation thirdLocation = new DataNodeLocation("A", 2);

        assertEquals(firstLocation, first.getLocation());
        assertEquals(secondLocation, second.getLocation());
        assertEquals(thirdLocation, third.getLocation());

        assertTrue(first.isRoot());
        assertFalse(second.isRoot());
        assertFalse(third.isRoot());
        assertEquals(3, tree.size());
    }

    @Test
    void testAddNode_whenSubNodesAdd_theyShouldBeAddedIntoNewLocation() {
        Tree tree = new Tree();

        TempDataNode parent = new TempDataNode("Parent");
        TempDataNode node1 = new TempDataNode("Node 1");
        TempDataNode node2 = new TempDataNode("Node 2");
        TempDataNode node3 = new TempDataNode("Node 3");

        parent.addSubNode(node1);
        parent.addSubNode(node2);
        parent.addSubNode(node3);

        tree.addNode(parent);

        DataNodeLocation parentLocation = new DataNodeLocation("A", 0);
        DataNodeLocation node1Location = new DataNodeLocation("B", 0);
        DataNodeLocation node2Location = new DataNodeLocation("B", 1);
        DataNodeLocation node3Location = new DataNodeLocation("B", 2);

        assertTrue(parent.isRoot());
        assertEquals(parentLocation, parent.getLocation());
        assertEquals(node1Location, node1.getLocation());
        assertEquals(node2Location, node2.getLocation());
        assertEquals(node3Location, node3.getLocation());
        assertEquals(4, tree.size());
    }

}