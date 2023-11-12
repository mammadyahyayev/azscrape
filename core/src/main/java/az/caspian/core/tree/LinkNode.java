package az.caspian.core.tree;

public class LinkNode extends Node {
    private final String name;
    private final String selector;
    private String link;

    public LinkNode(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
