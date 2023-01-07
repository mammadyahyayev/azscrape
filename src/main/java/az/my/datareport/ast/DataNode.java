package az.my.datareport.ast;

public class DataNode {
    private String url;
    private DataElement element;

    public DataNode() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataElement getElement() {
        return element;
    }

    public void setElement(DataElement element) {
        this.element = element;
    }
}
