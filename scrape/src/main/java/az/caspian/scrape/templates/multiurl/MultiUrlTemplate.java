package az.caspian.scrape.templates.multiurl;

import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import az.caspian.scrape.templates.ScrapeTemplate;

public class MultiUrlTemplate implements ScrapeTemplate {
  private final MultiUrlTemplateParameters templateParameters;
  private final DataTree<Node> tree;

  public MultiUrlTemplate(MultiUrlTemplateParameters templateParameters, DataTree<Node> tree) {
    this.templateParameters = templateParameters;
    this.tree = tree;
  }

  public MultiUrlTemplateParameters getTemplateParameters() {
    return templateParameters;
  }

  public DataTree<Node> getTree() {
    return tree;
  }

  @Override
  public String name() {
    return "MultiUrlTemplate";
  }

  @Override
  public boolean supportParallelExecution() {
    return false;
  }
}
