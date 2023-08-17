package az.caspian.core.tree;

public class KeyValueDataNode extends Node {
    private final String parent;
    private final String key;
    private final String value;

    public KeyValueDataNode(String keySelector, String valueSelector) {
        this.parent = "";
        this.key = keySelector;
        this.value = valueSelector;
    }

    public KeyValueDataNode(String parent, String key, String value) {
        this.parent = parent;
        this.key = key;
        this.value = value;
    }

    public String getParent() {
        return parent;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
