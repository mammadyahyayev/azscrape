package az.caspian.scrape.templates.pagination;

import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.scrape.templates.ScrapeTemplate;

import java.util.Objects;

public class PaginationTemplate implements ScrapeTemplate {
  private final PageParameters pageParameters;
  private final DataTree<Node> tree;

  public PaginationTemplate(PageParameters pageParameters, DataTree<Node> tree) {
    this.pageParameters = Objects.requireNonNull(pageParameters);
    this.tree = Objects.requireNonNull(tree);
  }

  public PageParameters getPageParameters() {
    return pageParameters;
  }

  public DataTree<Node> getTree() {
    return tree;
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
