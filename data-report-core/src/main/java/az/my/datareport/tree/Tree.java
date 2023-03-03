package az.my.datareport.tree;

import az.my.datareport.utils.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract syntax tree (AST)
 */
public class Tree {
    private final List<TempDataNode> dataNodeList;
    private int size = 0;


    public Tree() {
        dataNodeList = new ArrayList<>();
    }

    public void addNode(TempDataNode node) {
        Assert.required(node, "dataNode is required field");

        Assert.checkArgument(node.hasValue(), "node must have value in order to add it to the tree");

        if (size == 0) {
            DataNodeLocation firstLocation = DataNodeLocation.init();
            node.setLocation(firstLocation);
            node.setRoot(true);
        } else {
            DataNodeLocation lastLocation = this.getLast().getLocation();
            node.setLocation(lastLocation.nextLocation(false));
        }

        dataNodeList.add(node);
        this.size++;

        /*
             1. First check node value
             2. if dataNodeList is empty, then node must be the root
             3. future task: if there is prev value for example, user might go wrong, and pass a node to the list first
                which has multiple parents. In this case, you must find the first parent of the node (use iterator)
             4. future task: as well as there will be multiple next nodes, in that case, we should consider them
             5. After adding first element, it should be the root
         */
    }

    public void addNode(TempDataNode node, String location) {

    }

    public void deleteNode(TempDataNode node) {

    }

    public TempDataNode getFirst() {
        return this.dataNodeList.get(0);
    }

    public TempDataNode getLast() {
        return this.dataNodeList.get(this.size - 1);
    }

    public void getNext() {

    }

    public void getPrev() {

    }

    public void preOrder() {

    }

    public void postOrder() {

    }

    /**
     * Gives list of nodes in tree. Returned list shouldn't be
     * modified, otherwise it will throw {@link UnsupportedOperationException}
     *
     * @return list of nodes in tree
     */
    public List<TempDataNode> nodes() {
        return Collections.unmodifiableList(this.dataNodeList);
    }

    public int size() {
        return this.size;
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
    }

    //TODO: Create iterator for both pre and post order
}
