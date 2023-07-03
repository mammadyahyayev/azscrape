package az.caspian.core.tree;

import java.util.List;

/**
 * A Tree
 *
 * @param <N> A node
 */
public interface Tree<N> {
    void addChild(N child, N parent);

    N getRoot();

    List<N> getChildren(N parent);
}
