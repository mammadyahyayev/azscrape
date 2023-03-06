package az.my.datareport.tree;

/**
 * An attribute
 */
public class DataNodeAttribute {
    private final String name;
    private final String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)

    public DataNodeAttribute(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }
}
