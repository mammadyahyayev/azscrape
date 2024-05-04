package az.caspian.core.tree.node;

public class DataNodeBuilder {
    private String name;
    private String selector;

    public DataNodeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DataNodeBuilder selector(String selector) {
        this.selector = selector;
        return this;
    }

    public DataNode build() {
        return new DataNode(this.name, this.selector);
    }
}
