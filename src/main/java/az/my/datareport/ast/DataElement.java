package az.my.datareport.ast;

public class DataElement {
    private final String name;
    private final String selector;

    public DataElement(String name, String selector) {
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
