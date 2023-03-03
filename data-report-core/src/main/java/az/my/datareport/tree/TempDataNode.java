package az.my.datareport.tree;

import az.my.datareport.utils.Assert;
import com.google.common.base.Objects;

/**
 * A data node
 */
/* Name is temporary, it should be DataNode after
 * fully implementation of Tree, for now name is TempDataNode
 * in order not to break anything in application
 */
public class TempDataNode {
    private TempDataNode next;
    private TempDataNode prev;
    private Object value;
    private DataNodeLocation location;
    private boolean isRoot;

    public TempDataNode() {

    }

    public TempDataNode(Object value) {
        Assert.required(value, "node value cannot be null");

        this.value = value;
    }

    public TempDataNode(TempDataNode prev, Object value, TempDataNode next) {
        Assert.required(value, "node value cannot be null");
    }

    public void getParent() {

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
        return false;
    }

    public boolean hasParent() {
        return false;
    }

    public boolean hasValue() {
        return this.value != null;
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
