package az.my.datareport.ast;

import java.util.ArrayList;
import java.util.List;

public class DataAST {
    private List<DataElement> elements;

    public List<DataElement> getElements() {
        return new ArrayList<>(elements);
    }

    public void setElements(List<DataElement> elements) {
        this.elements = new ArrayList<>(elements);
    }
}
