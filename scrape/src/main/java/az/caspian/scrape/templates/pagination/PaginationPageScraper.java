package az.caspian.scrape.templates.pagination;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.node.Node;
import az.caspian.scrape.NodeExecutor;
import az.caspian.scrape.ScrapeFailedException;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;

import java.text.MessageFormat;
import java.util.List;

public class PaginationPageScraper extends AbstractScrapeTemplate<PaginationTemplate> {
  private final NodeExecutor executor = new NodeExecutor();

  public PaginationPageScraper() {
  }

  public PaginationPageScraper(ScrapeErrorCallback callback) {
    super(callback);
  }

  public DataTable scrape(PaginationTemplate paginationTemplate) {
    DataTable dataTable = new DataTable();

    String url = null;
    int current = 0;
    try (var browser = new WebBrowser()) {
      browser.open();

      var pageParameters = paginationTemplate.getPageParameters();
      for (current = pageParameters.startPage(); current <= pageParameters.endPage(); current++) {
        url = pageParameters.getPageUrl(current);

        WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

        List<Node> nodes = paginationTemplate.getTree().nodes();
        List<DataRow> dataRows = executor.executeNodes(nodes, page);
        dataTable.addAll(dataRows);
      }
    } catch (Exception e) {
      String message = MessageFormat.format(
        "Failed to scrape data from {0} in page {1}, Exception: {2}", url, current, e.getMessage()
      );

      if (callback != null) callback.handle(message, dataTable);

      throw new ScrapeFailedException(message, e);
    }

    return dataTable;
  }
}
