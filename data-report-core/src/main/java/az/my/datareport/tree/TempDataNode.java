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
public class TempDataNode {
    private DataNodeAttribute attribute;
    private DataNodeLocation location;
    private TempDataNode parentNode;
    private boolean isRoot;
    private final List<TempDataNode> subNodes = new ArrayList<>();

    public TempDataNode() {

    }

    public TempDataNode(DataNodeAttribute attribute) {
        Assert.required(attribute, "node attribute cannot be null");

        this.attribute = attribute;
    }

    public TempDataNode getParent() {
        return parentNode;
    }

    private void setParent(TempDataNode node) {
        this.parentNode = node;
    }

    public List<TempDataNode> subNodes() {
        return subNodes;
    }

    public TempDataNode getSubNode(int order) {
        return subNodes.get(order);
    }

    public void addSubNode(TempDataNode node) {
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

    public void setRoot(boolean root) {
        isRoot = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempDataNode that = (TempDataNode) o;
        return Objects.equal(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
}