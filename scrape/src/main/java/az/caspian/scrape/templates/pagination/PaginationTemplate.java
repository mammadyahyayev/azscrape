package az.caspian.scrape.templates.pagination;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.scrape.templates.ScrapeTemplate;

import java.util.Objects;

public class PaginationTemplate implements ScrapeTemplate  {
    private final PageParameters pageParameters;
    private final DataTree<DataNode> root;

    public PaginationTemplate(PageParameters pageParameters, DataTree<DataNode> root) {
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

    @Override
    public String name() {
        return "Pagination Template";
    }

    @Override
    public boolean supportParallelExecution() {
        return true;
    }
}
