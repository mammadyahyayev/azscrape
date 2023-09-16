package az.caspian.core.tree;

public class Node {
    private String name;
    private String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)

    public Node() {
    }

    public Node(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }

    public boolean isDataNode() {
        return this instanceof DataNode;
    }

    public boolean isParentNode() {
        return this instanceof ParentNode;
    }

    public boolean isKeyValueNode() {
        return this instanceof KeyValueDataNode;
    }

    public boolean isListNode() {
        return this instanceof ListNode;
    }

    public boolean isLinkNode() {
        return this instanceof LinkNode;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }
}
