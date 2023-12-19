package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.ListNode;
import az.caspian.core.tree.Node;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;

public class PaginationItemVisitorScraper
    extends AbstractScrapeTemplate<PaginationItemVisitorTemplate> {

  private ScrapeErrorCallback callback;
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

  public PaginationItemVisitorScraper() {}

  public PaginationItemVisitorScraper(ScrapeErrorCallback callback) {
    this.callback = callback;
  }

  public DataTable scrape() {
    return null;
  }

  @Override
  public DataTable scrape(PaginationItemVisitorTemplate template) {
    DataTable dataTable = new DataTable();

    String url = null;
    int current = 0;
    try (var browser = new WebBrowser()) {
      browser.open();

      var pageParameters = template.getPageParameters();
      List<DataRow> dataRows = new ArrayList<>();
      for (current = pageParameters.startPage(); current <= pageParameters.endPage(); current++) {
        url = pageParameters.getPageUrl(current);

        WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());
        DataTree<Node> tree = template.getTree();
        var rootNode = (ListNode) tree.nodes().get(0);
        List<WebElement> elements = page.fetchWebElements(rootNode.getSelector());
        for (WebElement element : elements) {
          String urlOfSubPage = safeGetAttribute(element, "href");
          if(urlOfSubPage == null) continue;
          WebPage webPage = browser.goTo(urlOfSubPage, pageParameters.getDelayBetweenPages());
          DataRow dataRow = collector.collect(rootNode.getChildren(), webPage);
          dataRows.add(dataRow);
          browser.backToPrevPage();
        }
      }
      dataTable.addAll(dataRows);
    } catch (Exception e) {
      String message =
          MessageFormat.format(
              "Failed to scrape data from {0} in page {1}, Exception: {2}",
              url, current, e.getMessage());

      if (callback != null) callback.handle(message, dataTable);

      throw new RuntimeException(message, e);
    }

    return dataTable;
  }

  private String safeGetAttribute(final WebElement element, final String attributeType) {
    String attribute = null;

    try {
      attribute = element.getAttribute(attributeType);
    } catch (Exception e) {
      // Ignore: It is highly possible to get Exception in pages.
      //TODO: Log it
      return attribute;
    }

    return attribute;
  }
}
