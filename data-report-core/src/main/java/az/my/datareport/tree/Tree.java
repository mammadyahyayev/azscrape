package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract syntax tree (AST)
 */
public class Tree {
    private final List<DataNode> dataNodeList;

    public Tree() {
        dataNodeList = new ArrayList<>();
    }

    public void addNode(DataNode node) {
        Asserts.required(node, "dataNode is required field");

        // find node of given node
        // TempDataNode node = findParent(node); //TODO: method should be getAncestor inside tempDataNode

        if (dataNodeList.size() == 0) {
            DataNodeLocation firstLocation = DataNodeLocation.init();
            node.setLocation(firstLocation);
            node.setRoot(true);
        } else {
            /* TODO: get the last element in the dataNodeList and create a new location
                based on previous one, with the next order and same level
            */

            // TODO: if this node has subNodes, it must check first there is a level for its subNodes.
            //  for instance, SubNodes must be on the Level B, then it will get the next level by using its location.
            //  then, it will search for all nodes to find the last value in the second level (level B), then will add
            //  items into level B with the last unused order.
        }

        if (node.hasSubNode()) {
            DataNodeLocation lastLocation = node.getLocation();
            String nextLevel = lastLocation.nextLevel();

            for (int i = 0; i < node.subNodes().size(); i++) {
                DataNode prev = i > 0 ? node.getSubNode(i - 1) : null;
                DataNode curr = node.getSubNode(i);

                if (i == 0) {
                    curr.setLocation(new DataNodeLocation(nextLevel, 0));
                    continue;
                }

                curr.setLocation(new DataNodeLocation(nextLevel, prev.getLocation().nextOrder()));
            }
        }

        dataNodeList.add(node);

        /*
             1. First check node value
             2. if dataNodeList is empty, then node must be the root
             3. future task: if there is prev value for example, user might go wrong, and pass a node to the list first
                which has multiple parents. In this case, you must find the first node of the node (use iterator)
             4. future task: as well as there will be multiple next nodes, in that case, we should consider them
             5. After adding first element, it should be the root
         */
    }

    private DataNode findParent(DataNode node) {
        if (node.getParent() == null) {
            return node;
        }

        return findParent(node.getParent());
    }

    public void addNode(DataNode node, String location) {
        Asserts.required(node, "node field is required");
        Asserts.required(location, "location field is required");

        DataNodeLocation nodeLocation;
        DataNode lastNodeInLocation = getNodeFrom(location); //TODO: it will search every time when there are so many sub nodes
        if (lastNodeInLocation == null) {
            nodeLocation = new DataNodeLocation(location, 0);
        } else {
            nodeLocation = new DataNodeLocation(location, lastNodeInLocation.getLocation().getOrder());
        }

        node.setLocation(nodeLocation);
        this.dataNodeList.add(node);
    }

    public void deleteNode(DataNode node) {
        throw new UnsupportedOperationException();
    }

    public DataNode getNodeFrom(String location) {
        //TODO: first it will search whether there is a location or not, if given location is far from
        // the locations which already have, then throw exception, e.g. in tree last level is B, if user
        // trys to add node into the tree with level Z, then throw Exception
        // msg: failed to add, last level in the tree is B, therefore next node can be ad only A, B, or C
        throw new UnsupportedOperationException();
    }

    public void getNext() {
        throw new UnsupportedOperationException();
    }

    public void getPrev() {
        throw new UnsupportedOperationException();
    }

    public void preOrder() {
        throw new UnsupportedOperationException();
    }

    public void postOrder() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gives list of nodes in tree. Returned list shouldn't be
     * modified, otherwise it will throw {@link UnsupportedOperationException}
     *
     * @return list of nodes in tree
     */
    public List<DataNode> nodes() {
        return Collections.unmodifiableList(this.dataNodeList);
    }

    /**
     * List the nodes from given level
     * <p>
     * for instance, if level is A, it will print nodes
     * that located in level A.
     *
     * @param level
     */
    public void nodes(String level) {
        throw new UnsupportedOperationException();
    }

    /**
     * List the nodes from given level to the bottom
     * <p>
     * for instance, if level is A, it will print nodes
     * that located in level A.
     *
     * @param level
     */
    public void nodesToBottom(String level) {
        throw new UnsupportedOperationException();
    }

    /**
     * List the nodes from given level to the up
     * <p>
     * for instance, if level is A, it will print nodes
     * that located in level A.
     *
     * @param level
     */
    public void nodesToUp(String level) {
        throw new UnsupportedOperationException();
    }

    /**
     * Represents the longest path from root to bottom
     * <p>
     * for instance, if there is a level E, height will be
     * from root - E
     */
    public void height() {
        //TODO: find a method for calculating
        // nodes in each level through to the bottom
        // and return int value, which is great way to compare trees
        throw new UnsupportedOperationException();
    }

    //TODO: Create iterator for both pre and post order

    //TODO: Create getRoot() method
}
