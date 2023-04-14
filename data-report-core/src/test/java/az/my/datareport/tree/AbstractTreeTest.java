package az.my.datareport.tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTreeTest {

    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testAddNode_whenNodesAdded_returnExactSizeOfTree() {
        AbstractTree abstractTree = new AbstractTree();

        abstractTree.addNode(new DataNode(new DataNodeAttribute("First Node", null)));
        abstractTree.addNode(new DataNode(new DataNodeAttribute("Second Node", null)));
    }

    @Test
    void testNodes_whenModifyingReturnedList_throwException() {
        AbstractTree abstractTree = new AbstractTree();

        abstractTree.addNode(new DataNode(new DataNodeAttribute("First Node", null)));
        abstractTree.addNode(new DataNode(new DataNodeAttribute("Second Node", null)));

        List<DataNode> nodes = abstractTree.nodes();

        assertThrows(UnsupportedOperationException.class, () ->
                nodes.add(new DataNode(new DataNodeAttribute("Third Node", null))));
    }

    //TODO: Change this method after implementation of location, order, level
    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testGetFirst() {
        AbstractTree abstractTree = new AbstractTree();

        DataNode first = new DataNode(new DataNodeAttribute("First Node", null));
        DataNode second = new DataNode(new DataNodeAttribute("Second Node", null));

        abstractTree.addNode(first);
        abstractTree.addNode(second);
    }

    //TODO: Change this method after implementation of location, order, level
    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testGetLast() {
        AbstractTree abstractTree = new AbstractTree();

        DataNode first = new DataNode(new DataNodeAttribute("First Node", null));
        DataNode second = new DataNode(new DataNodeAttribute("Second Node", null));

        abstractTree.addNode(first);
        abstractTree.addNode(second);
    }

    @Test
    void testAddNode_whenNodeAddedFirstTime_returnInitialLocation() {
        AbstractTree abstractTree = new AbstractTree();
        DataNode node = new DataNode(new DataNodeAttribute("First Node", null));
        abstractTree.addNode(node);

        DataNodeLocation initialLocation = new DataNodeLocation("A", 0);
        assertEquals(initialLocation, node.getLocation());
        assertTrue(node.isRoot());
    }

    @Disabled("Doesn't support multiple root values for now")
    @Test
    void testAddNode_whenNodesAdded_returnTheirLocations() {
        AbstractTree abstractTree = new AbstractTree();

        DataNode first = new DataNode(new DataNodeAttribute("First Node", null));
        DataNode second = new DataNode(new DataNodeAttribute("Second Node", null));
        DataNode third = new DataNode(new DataNodeAttribute("Third Node", null));
        abstractTree.addNode(first);
        abstractTree.addNode(second);
        abstractTree.addNode(third);

        DataNodeLocation firstLocation = new DataNodeLocation("A", 0);
        DataNodeLocation secondLocation = new DataNodeLocation("A", 1);
        DataNodeLocation thirdLocation = new DataNodeLocation("A", 2);

        assertEquals(firstLocation, first.getLocation());
        assertEquals(secondLocation, second.getLocation());
        assertEquals(thirdLocation, third.getLocation());

        assertTrue(first.isRoot());
        assertFalse(second.isRoot());
        assertFalse(third.isRoot());
    }

    @Test
    void testAddNode_whenSubNodesAdd_theyShouldBeAddedIntoNewLocation() {
        AbstractTree abstractTree = new AbstractTree();

        DataNode parent = new DataNode(new DataNodeAttribute("parent", ".repo-list-item"));
        DataNode node1 = new DataNode(new DataNodeAttribute("title", ".v-align-middle"));
        DataNode node2 = new DataNode(new DataNodeAttribute("description", ".mb-1"));

        parent.addSubNode(node1);
        parent.addSubNode(node2);

        abstractTree.addNode(parent);

        DataNodeLocation parentLocation = new DataNodeLocation("A", 0);
        DataNodeLocation node1Location = new DataNodeLocation("B", 0);
        DataNodeLocation node2Location = new DataNodeLocation("B", 1);

        assertTrue(parent.isRoot());
        assertEquals(parentLocation, parent.getLocation());
        assertEquals(node1Location, node1.getLocation());
        assertEquals(node2Location, node2.getLocation());
        assertEquals(1, abstractTree.nodes().size());

        assertEquals(parent, abstractTree.nodes().get(0));
        assertEquals(node1, abstractTree.nodes().get(0).getSubNode(0));
        assertEquals(node2, abstractTree.nodes().get(0).getSubNode(1));
    }

}