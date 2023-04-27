package az.my.datareport.tree;

/**
 * A data node
 */
public class DataNode {
    private final String name;
    private final String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)
    private final boolean isKeyColumn;

    public DataNode(String name, String selector) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = false;
    }

    public DataNode(String name, String selector, boolean isKeyColumn) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = true;
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
}
