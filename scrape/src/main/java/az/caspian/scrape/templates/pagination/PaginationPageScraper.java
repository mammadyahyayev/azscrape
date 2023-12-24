package az.caspian.scrape.templates.pagination;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.ListNode;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PaginationPageScraper extends AbstractScrapeTemplate<PaginationTemplate> {
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

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
      List<DataRow> dataRows = new ArrayList<>();
      for (current = pageParameters.startPage(); current <= pageParameters.endPage(); current++) {
        url = pageParameters.getPageUrl(current);

        WebPage page = browser.goTo(url, pageParameters.getDelayBetweenPages());

        var rootNode = (ListNode) paginationTemplate.getTree().nodes().get(0);
        List<WebElement> webElements = page.fetchWebElements(rootNode.getSelector());
        for (WebElement webElement : webElements) {
          DataRow dataRow = collector.collect(rootNode.getChildren(), webElement);
          dataRows.add(dataRow);
        }
      }
      dataTable.addAll(dataRows);
    } catch (Exception e) {
      String message = MessageFormat.format(
        "Failed to scrape data from {0} in page {1}, Exception: {2}", url, current, e.getMessage()
      );

      if (callback != null) callback.handle(message, dataTable);

      throw new RuntimeException(message, e);
    }

    return dataTable;
  }
}
