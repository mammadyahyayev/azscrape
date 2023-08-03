package az.caspian.core.tree;

import java.util.*;
import java.util.function.Predicate;

public class DataTree<N extends Node> implements Tree<N> {
    private final N node;
    private DataTree<N> parent;
    private final List<DataTree<N>> subNodes;

    private final Map<N, List<N>> nodes;

    private final N root;

    public DataTree(N node) {
        this.node = node;
        this.root = node;
        this.subNodes = new ArrayList<>();
        this.nodes = new HashMap<>();

        node.setLocation(DataNodeLocation.first());
    }

    public void addSubNode(DataTree<N> node) {
        node.parent = this;
        this.subNodes.add(node);
    }

    public void addChild(N child, N parent) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("child and parent nodes must be not null!");
        }

        if (!nodes.containsKey(parent)) {
            nodes.put(parent, new ArrayList<>());
            parent.setParent(true);

            DataNodeLocation parentLocation = parent.getLocation();
            child.setLocation(parentLocation.nextLocation(true));
        } else {
            List<N> children = nodes.get(parent);
            if (!children.isEmpty()) {
                N lastChild = children.get(children.size() - 1);
                child.setLocation(lastChild.getLocation().nextLocation());
            }
        }

        nodes.get(parent).add(child);
    }

    /**
     * Finds the first node that matched the condition
     *
     * @param condition a condition
     * @return Optional
     */
    public Optional<N> find(Predicate<N> condition) {
        if (condition.test(root)) {
            return Optional.of(root);
        }

        List<N> ns = nodes.get(root);
        for (N n : ns) {
            if (condition.test(n)) return Optional.of(n);
        }

        return Optional.empty();
    }

    @Override
    public N getRoot() {
        return this.root;
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

    @Override
    public List<N> getChildren(N parent) {
        if (parent == null) throw new IllegalArgumentException("parent node must be not null!");
        return nodes.getOrDefault(parent, Collections.emptyList());
    }

    public boolean hasSubNode() {
        return this.subNodes.size() > 0;
    }

    public boolean isRoot() {
        return this.parent == null;
    }
}
