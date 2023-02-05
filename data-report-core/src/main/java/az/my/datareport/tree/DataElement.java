package az.my.datareport.tree;

import java.util.ArrayList;
import java.util.List;

public class DataElement {
    private String name;
    private String selector;
    private List<DataElement> children = new ArrayList<>(); //TODO: convert it to Set

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

    public List<DataElement> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setChildren(List<DataElement> children) {
        this.children = children;
    }
}
