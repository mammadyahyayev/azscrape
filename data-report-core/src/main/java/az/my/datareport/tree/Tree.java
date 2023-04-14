package az.my.datareport.tree;

import java.util.List;

public interface Tree {

    /**
     * Adds dataNode into Tree with location and level,
     * if dataNode has sub nodes, those will be defined
     * inside Tree with location and level.
     *
     * @param dataNode a node
     */
    void addNode(DataNode dataNode);

    /**
     * Retrieve all nodes inside Tree
     *
     * @return list of nodes
     */
    List<DataNode> nodes();
}
