package az.my.datareport.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataTree<N> implements Tree<N> {
    private final N node;
    private DataTree<N> parent;
    private final List<DataTree<N>> subNodes;

    public DataTree(N node) {
        this.node = node;
        this.subNodes = new ArrayList<>();
    }

    public void addSubNode(DataTree<N> node) {
        node.parent = this;
        this.subNodes.add(node);
    }

    public N value() {
        return this.node;
    }

    public DataTree<N> parent() {
        return this.parent;
    }

    public List<DataTree<N>> nodes() {
        return Collections.unmodifiableList(subNodes);
    }

    public boolean hasSubNode() {
        return this.subNodes.size() > 0;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

}
