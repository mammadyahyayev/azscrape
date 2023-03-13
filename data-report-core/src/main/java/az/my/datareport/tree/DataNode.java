package az.my.datareport.tree;

import az.my.datareport.utils.Assert;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A data node
 */
/* Name is temporary, it should be DataNode after
 * fully implementation of Tree, for now name is TempDataNode
 * in order not to break anything in application
 */
public class DataNode {
    private final List<DataNode> subNodes = new ArrayList<>();
    private DataNodeAttribute attribute;
    private DataNodeLocation location;
    private DataNode parentNode;
    private boolean isRoot;

    public DataNode() {

    }

    public DataNode(DataNodeAttribute attribute) {
        Assert.required(attribute, "node attribute cannot be null");

        this.attribute = attribute;
    }

    public DataNode getParent() {
        return parentNode;
    }

    private void setParent(DataNode node) {
        this.parentNode = node;
    }

    public List<DataNode> subNodes() {
        return subNodes;
    }

    public DataNode getSubNode(int order) {
        return subNodes.get(order);
    }

    public void addSubNode(DataNode node) {
        Assert.required(node, "node field is required");

        node.setParent(this);
        this.subNodes.add(node);
    }

    /**
     * Indicates node is in the first place of the {@code tree}
     *
     * @return true if node is root node in the tree, otherwise false
     * @see Tree
     */
    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean hasSubNode() {
        return this.subNodes.size() > 0;
    }

    public boolean hasParent() {
        return false;
    }

    public void predecessor() {

    }

    public void successor() {

    }

    public DataNodeLocation getLocation() {
        return this.location;
    }

    public void setLocation(DataNodeLocation location) {
        this.location = location;
    }

    public DataNodeAttribute getAttribute() {
        return attribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataNode that = (DataNode) o;
        return Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
}
