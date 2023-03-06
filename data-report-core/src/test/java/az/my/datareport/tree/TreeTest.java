package az.my.datareport.tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testAddNode_whenNodesAdded_returnExactSizeOfTree() {
        Tree tree = new Tree();

        tree.addNode(new TempDataNode(new DataNodeAttribute("First Node", null)));
        tree.addNode(new TempDataNode(new DataNodeAttribute("Second Node", null)));

        assertEquals(2, tree.size());
    }

    @Test
    void testNodes_whenModifyingReturnedList_throwException() {
        Tree tree = new Tree();

        tree.addNode(new TempDataNode(new DataNodeAttribute("First Node", null)));
        tree.addNode(new TempDataNode(new DataNodeAttribute("Second Node", null)));

        List<TempDataNode> nodes = tree.nodes();

        assertThrows(UnsupportedOperationException.class, () ->
                nodes.add(new TempDataNode(new DataNodeAttribute("Third Node", null))));
    }

    //TODO: Change this method after implementation of location, order, level
    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testGetFirst() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode(new DataNodeAttribute("First Node", null));
        TempDataNode second = new TempDataNode(new DataNodeAttribute("Second Node", null));

        tree.addNode(first);
        tree.addNode(second);

        assertEquals(first, tree.getFirst());
    }

    //TODO: Change this method after implementation of location, order, level
    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testGetLast() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode(new DataNodeAttribute("First Node", null));
        TempDataNode second = new TempDataNode(new DataNodeAttribute("Second Node", null));

        tree.addNode(first);
        tree.addNode(second);

        assertEquals(second, tree.getLast());
    }

    @Test
    void testAddNode_whenNodeAddedFirstTime_returnInitialLocation() {
        Tree tree = new Tree();
        TempDataNode node = new TempDataNode(new DataNodeAttribute("First Node", null));
        tree.addNode(node);

        DataNodeLocation initialLocation = new DataNodeLocation("A", 0);
        assertEquals(initialLocation, node.getLocation());
        assertTrue(node.isRoot());
    }

    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testAddNode_whenNodesAdded_returnTheirLocations() {
        Tree tree = new Tree();

        TempDataNode first = new TempDataNode(new DataNodeAttribute("First Node", null));
        TempDataNode second = new TempDataNode(new DataNodeAttribute("Second Node", null));
        TempDataNode third = new TempDataNode(new DataNodeAttribute("Third Node", null));
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

        TempDataNode parent = new TempDataNode(new DataNodeAttribute("parent", ".repo-list-item"));
        TempDataNode node1 = new TempDataNode(new DataNodeAttribute("title", ".v-align-middle"));
        TempDataNode node2 = new TempDataNode(new DataNodeAttribute("description", ".mb-1"));

        parent.addSubNode(node1);
        parent.addSubNode(node2);

        tree.addNode(parent);

        DataNodeLocation parentLocation = new DataNodeLocation("A", 0);
        DataNodeLocation node1Location = new DataNodeLocation("B", 0);
        DataNodeLocation node2Location = new DataNodeLocation("B", 1);

        assertTrue(parent.isRoot());
        assertEquals(parentLocation, parent.getLocation());
        assertEquals(node1Location, node1.getLocation());
        assertEquals(node2Location, node2.getLocation());
        assertEquals(3, tree.size());
    }

}