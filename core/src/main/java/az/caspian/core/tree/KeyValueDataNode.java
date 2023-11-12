package az.caspian.core.tree;

public class KeyValueDataNode extends Node {
    private final String keySelector;
    private final String valueSelector;
    private String value;

    public KeyValueDataNode(String keySelector, String valueSelector) {
        this.keySelector = keySelector;
        this.valueSelector = valueSelector;
    }

    public String getKeySelector() {
        return keySelector;
    }

    public String getValueSelector() {
        return valueSelector;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
