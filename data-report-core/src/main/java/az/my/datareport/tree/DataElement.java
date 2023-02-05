package az.my.datareport.tree;

import java.util.HashSet;
import java.util.Set;

public class DataElement {
    private String name;
    private String selector;
    private Set<DataElement> children = new HashSet<>();

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

    public Set<DataElement> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public void setChildren(Set<DataElement> children) {
        this.children = children;
    }
}
