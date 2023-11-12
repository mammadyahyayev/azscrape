package az.caspian.scrape.templates.pagination;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.ListNode;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;

public class PaginationPageScraper extends AbstractScrapeTemplate<PaginationTemplate> {

  private final ScrapedDataCollector collector = new ScrapedDataCollector();
  private ScrapeErrorCallback callback;

  public PaginationPageScraper() {}

  public PaginationPageScraper(ScrapeErrorCallback callback) {
    this.callback = callback;
  }

  public ReportDataTable scrape(PaginationTemplate paginationTemplate) {
    ReportDataTable reportDataTable = new ReportDataTable();

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
      reportDataTable.addAll(dataRows);
    } catch (Exception e) {
      String message =
          MessageFormat.format(
              "Failed to scrape data from {0} in page {1}, Exception: {2}",
              url, current, e.getMessage());

      if (callback != null) callback.handle(message, reportDataTable);

      throw new RuntimeException(message, e);
    }

    return reportDataTable;
  }
}
