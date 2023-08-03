package az.caspian.scrape.templates.scroll;

import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.scrape.templates.ScrapeTemplate;

import java.util.Objects;

public class ScrollablePageTemplate implements ScrapeTemplate {
    private final ScrollablePageParameters pageParameters;
    private final DataTree<Node> root;

    public ScrollablePageTemplate(ScrollablePageParameters pageParameters, DataTree<Node> root) {
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

    public DataTree<Node> getRoot() {
        return root;
    }

    @Override
    public String name() {
        return "Scrollable Page Template";
    }

    @Override
    public boolean supportParallelExecution() {
        return false;
    }
}
