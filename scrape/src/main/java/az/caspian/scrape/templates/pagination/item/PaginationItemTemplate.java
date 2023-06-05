package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.scrape.templates.ScrapeTemplate;
import az.caspian.scrape.templates.pagination.PageParameters;
import az.caspian.scrape.templates.pagination.PaginationTemplate;

public class PaginationItemTemplate extends PaginationTemplate implements ScrapeTemplate {

    public PaginationItemTemplate(PageParameters pageParameters, DataTree<DataNode> root) {
        super(pageParameters, root);
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
