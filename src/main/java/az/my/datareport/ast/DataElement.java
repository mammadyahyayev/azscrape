package az.my.datareport.ast;

public class DataElement {
    private final String name;
    private final DataType type;
    private final DataSelectorType selector_type;
    private final String selector;

    public DataElement(String name, DataType type,
                       DataSelectorType selector_type, String selector) {
        this.name = name;
        this.type = type;
        this.selector_type = selector_type;
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public DataSelectorType getSelector_type() {
        return selector_type;
    }

    public String getSelector() {
        return selector;
    }
}
