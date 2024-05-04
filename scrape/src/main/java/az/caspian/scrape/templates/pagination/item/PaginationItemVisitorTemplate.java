package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.node.Node;
import az.caspian.scrape.templates.pagination.PageParameters;
import az.caspian.scrape.templates.pagination.PaginationTemplate;

public class PaginationItemVisitorTemplate extends PaginationTemplate implements ScrapeTemplate {

  public PaginationItemVisitorTemplate(PageParameters pageParameters, DataTree<Node> tree) {
    super(pageParameters, tree);

    Node root = tree.nodes().get(0);
    if (!root.isListNode()) {
      throw new IllegalStateException("Root element of '" + name() + "' must be ListNode!");
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
