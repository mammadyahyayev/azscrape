package az.my.datareport.tree;

import az.my.datareport.utils.Asserts;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Pagination {
    private final PageParameters pageParameters;
    private final DataTree<DataNode> root;

    public Pagination(PageParameters pageParameters, DataTree<DataNode> root) {
        this.pageParameters = Objects.requireNonNull(pageParameters);
        this.root = Objects.requireNonNull(root);
    }

    /**
     * Returns PageParameters object which keeps page related
     * properties.
     *
     * @return a {@code PageParameters} object
     * @see PageParameters
     */
    public PageParameters getPageParameters() {
        return pageParameters;
    }
    public DataTree<DataNode> getRoot() {
        return root;
    }
}
