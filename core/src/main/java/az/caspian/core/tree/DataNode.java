package az.caspian.core.tree;

/**
 * A data node
 */
public class DataNode {
    private final String name;
    private final String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)
    private final boolean isKeyColumn;
    private boolean isRoot;
    private boolean isLink;

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

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setLink(boolean link) {
        isLink = link;
    }

    public boolean isLink() {
        return isLink;
    }
}
