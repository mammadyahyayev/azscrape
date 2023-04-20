package az.my.datareport.tree;

import java.util.List;

public interface Tree<NODE extends Node> {

    /**
     * Adds dataNode into Tree with location and level,
     * if dataNode has sub nodes, those will be defined
     * inside Tree with location and level.
     *
     * @param node a node
     */
    void addNode(NODE node);

    /**
     * Retrieve all nodes inside Tree
     *
     * @return list of nodes
     */
    List<NODE> nodes();
}
