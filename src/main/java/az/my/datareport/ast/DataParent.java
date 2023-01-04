package az.my.datareport.ast;

import java.util.List;

public class DataParent {
    private String selector;
    private List<DataElement> children;

    public DataParent() {

    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public List<DataElement> getChildren() {
        return children;
    }

    public void setChildren(List<DataElement> children) {
        this.children = children;
    }
}
