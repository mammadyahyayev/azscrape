package az.caspian.scrape.templates.scroll;

import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;

import java.util.Objects;

public class ScrollablePageTemplate implements ScrapeTemplate {
    private final ScrollablePageParameters pageParameters;
    private final DataTree<Node> tree;

    public ScrollablePageTemplate(ScrollablePageParameters pageParameters, DataTree<Node> tree) {
        this.pageParameters = Objects.requireNonNull(pageParameters);
        this.tree = Objects.requireNonNull(tree);
    }

    public ScrollablePageParameters getPageParameters() {
        return pageParameters;
    }

    public DataTree<Node> getTree() {
        return tree;
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
