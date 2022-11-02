package az.my.datareport.ast;

public class DataElement {
    private String name;
    private String selector;

    public DataElement() {

    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
