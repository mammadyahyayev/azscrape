package az.caspian.core.tree;

/**
 * A data node
 */
public class DataNode {
    private String name;
    private String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)
    private boolean isKeyColumn;
    private boolean isParent;
    private boolean isLink; // TODO: Create separate node: DataLinkNode (if necessary)
    private boolean isKeyValuePair; //TODO: Create separate class: DataKeyValuePairNode
    private DataNodeLocation location;

    public DataNode() {

    }

    public DataNode(String name, String selector) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = false;
    }

    public DataNode(String name, String selector, boolean isKeyColumn) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = isKeyColumn;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }

    public boolean isKeyColumn() {
        return isKeyColumn;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setLink(boolean link) {
        isLink = link;
    }

    public boolean isLink() {
        return isLink;
    }

    public void setLocation(DataNodeLocation location) {
        this.location = location;
    }

    public DataNodeLocation getLocation() {
        return location;
    }

    public boolean isKeyValuePair() {
        return isKeyValuePair;
    }

    public void setKeyValuePair(boolean keyValuePair) {
        isKeyValuePair = keyValuePair;
    }
}
