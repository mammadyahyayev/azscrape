package az.caspian.scrape.templates.scroll;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.ListNode;
import az.caspian.core.tree.ReportDataTable;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ScrollablePageScraper extends AbstractScrapeTemplate<ScrollablePageTemplate> {

  private ScrapeErrorCallback callback;
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

  public ScrollablePageScraper() {}

  public ScrollablePageScraper(ScrapeErrorCallback callback) {
    this.callback = callback;
  }

  public ReportDataTable scrape(ScrollablePageTemplate scrollablePageTemplate) {
    ReportDataTable reportDataTable = new ReportDataTable();

    ScrollablePageParameters pageParameters = scrollablePageTemplate.getPageParameters();

    try (WebBrowser browser = new WebBrowser()) {
      browser.open();

      WebPage webPage = browser.goTo(pageParameters.getUrl());

      long previousHeight = 0;
      long currentHeight = webPage.height();

      List<DataRow> dataRows = new ArrayList<>();
      while (currentHeight != previousHeight) {
        int tryCount = 0;

        var rootNode = (ListNode) scrollablePageTemplate.getTree().nodes().get(0);
        List<WebElement> webElements = webPage.fetchWebElements(rootNode.getSelector());
        for (WebElement webElement : webElements) {
          DataRow row = collector.collect(rootNode.getChildren(), webElement);
          dataRows.add(row);
        }
        reportDataTable.addAll(dataRows);
        try {
          while (tryCount < 30) { // TODO: create new element to detect scroll react to end or not
            webPage.scroll(200);
            Thread.sleep(2000);
            tryCount++;
          }

          Thread.sleep(5000);
          webPage.scrollToEnd();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }

        previousHeight = currentHeight;
        currentHeight = webPage.height();
      }
    } catch (Exception e) {
      String message =
          MessageFormat.format(
              "Failed to scrape data from {0}, Exception: {1}",
              pageParameters.getUrl(), e.getMessage());

      if (callback != null) callback.handle(message, reportDataTable);

      throw new RuntimeException(message, e);
    }

    return reportDataTable;
  }
}
