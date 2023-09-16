package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.scrape.templates.ScrapeTemplate;
import az.caspian.scrape.templates.pagination.PageParameters;
import az.caspian.scrape.templates.pagination.PaginationTemplate;

public class PaginationItemVisitorTemplate extends PaginationTemplate implements ScrapeTemplate {

    public PaginationItemVisitorTemplate(PageParameters pageParameters, DataTree<Node> tree) {
        super(pageParameters, tree);

        Node root = tree.getRoot();
        if (!root.isLinkNode()) {
            throw new IllegalStateException("Root element of '" + name() + "' must be LinkNode!");
        }
    }

    @Override
    public String name() {
        return "Pagination Item Visitor Template";
    }

    @Override
    public boolean supportParallelExecution() {
        return false;
    }
}
