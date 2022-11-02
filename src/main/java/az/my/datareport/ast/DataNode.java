package az.my.datareport.ast;

import java.util.ArrayList;
import java.util.List;

public class DataNode {
    private String url;
    private List<DataElement> elements = new ArrayList<>();

    public DataNode() {

    }

    public List<DataElement> getElements() {
        return new ArrayList<>(elements);
    }

    public void setElements(List<DataElement> elements) {
        this.elements = elements;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
