package az.my.datareport.scrape.templates.scroll;

import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataTree;

import java.util.Objects;

public class ScrollablePageTemplate {
    private final ScrollablePageParameters pageParameters;
    private final DataTree<DataNode> root;

    public ScrollablePageTemplate(ScrollablePageParameters pageParameters, DataTree<DataNode> root) {
        this.pageParameters = Objects.requireNonNull(pageParameters);
        this.root = Objects.requireNonNull(root);
    }

    /**
     * Returns ScrollablePageParameters object which keeps page related
     * properties.
     *
     * @return a {@code ScrollablePageParameters} object
     * @see ScrollablePageParameters
     */
    public ScrollablePageParameters getPageParameters() {
        return pageParameters;
    }

    public DataTree<DataNode> getRoot() {
        return root;
    }
}
