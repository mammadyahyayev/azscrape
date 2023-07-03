package az.caspian.core.tree;

public class DataNodeBuilder {
    private String name;
    private String selector;
    private boolean isKeyColumn;
    private boolean isRoot;
    private boolean isLink;

    public DataNodeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DataNodeBuilder selector(String selector) {
        this.selector = selector;
        return this;
    }

    public DataNodeBuilder isKeyColumn(boolean isKeyColumn) {
        this.isKeyColumn = isKeyColumn;
        return this;
    }

    public DataNodeBuilder isRoot(boolean isRoot) {
        this.isRoot = isRoot;
        return this;
    }

    public DataNodeBuilder isLink(boolean isLink) {
        this.isLink = isLink;
        return this;
    }

    public DataNode build() {
        DataNode node = new DataNode(this.name, this.selector, this.isKeyColumn);
        node.setLink(this.isLink);
        node.setParent(this.isRoot);
        return node;
    }
}
