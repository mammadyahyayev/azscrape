package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A data node
 */
public class DataNode {
    private final String name;
    private final String selector; //TODO: Replace this with custom class (e.g. DataNodeSelector)

    public DataNode(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }

    public String getName() {
        return name;
    }

    public String getSelector() {
        return selector;
    }
}
