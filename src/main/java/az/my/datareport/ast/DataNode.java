package az.my.datareport.ast;

public class DataNode {
    private String url;
    private DataParent parent;

    public DataNode() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataParent getParent() {
        return parent;
    }

    public void setParent(DataParent parent) {
        this.parent = parent;
    }

    public String getParentSelector() {
        return this.parent.getSelector();
    }
}
