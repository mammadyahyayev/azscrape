package az.caspian.core.tree;

/**
 * A data node
 */
public class DataNode {
    private String name;
    private String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)
    private boolean isKeyColumn;
    private boolean isRoot;
    private boolean isLink;

    public DataNode() {

    }

    public DataNode(String name, String selector) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = false;
    }

    public DataNode(String name, String selector, boolean isKeyColumn) {
        this.name = name;
        this.selector = selector;
        this.isKeyColumn = isKeyColumn;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }

    public boolean isKeyColumn() {
        return isKeyColumn;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setLink(boolean link) {
        isLink = link;
    }

    public boolean isLink() {
        return isLink;
    }

    public static class Builder {
        private String name;
        private String selector;
        private boolean isKeyColumn;
        private boolean isRoot;
        private boolean isLink;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder selector(String selector) {
            this.selector = selector;
            return this;
        }

        public Builder isKeyColumn(boolean isKeyColumn) {
            this.isKeyColumn = isKeyColumn;
            return this;
        }

        public Builder isRoot(boolean isRoot) {
            this.isRoot = isRoot;
            return this;
        }

        public Builder isLink(boolean isLink) {
            this.isLink = isLink;
            return this;
        }

        public DataNode build() {
            DataNode node = new DataNode(this.name, this.selector, this.isKeyColumn);
            node.setLink(this.isLink);
            node.setRoot(this.isRoot);
            return node;
        }
    }
}
