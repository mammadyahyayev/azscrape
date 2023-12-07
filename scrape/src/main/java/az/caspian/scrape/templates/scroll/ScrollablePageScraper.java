package az.caspian.scrape.templates.scroll;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.*;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.TemplateException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.WebElement;

public class ScrollablePageScraper extends AbstractScrapeTemplate<ScrollablePageTemplate> {

  private ScrapeErrorCallback callback;
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

  public ScrollablePageScraper() {}

  public ScrollablePageScraper(ScrapeErrorCallback callback) {
    this.callback = callback;
  }

  public ReportDataTable scrape(final ScrollablePageTemplate template) {
    ReportDataTable reportDataTable = new ReportDataTable();
    ScrollablePageParameters pageParameters = template.getPageParameters();

    try (WebBrowser browser = new WebBrowser()) {
      browser.open();

      WebPage webPage = browser.goTo(pageParameters.getUrl());
      DataTree<Node> tree = template.getTree();
      Optional<Node> node = tree.findNode(Tree.NodeType.LIST);
      if (node.isEmpty()) {
        throw new TemplateException("The first node of " + template.name() + " must be ListNode.");
      }
      List<DataRow> dataRows = new ArrayList<>();

      var listNode = (ListNode) node.get();

      long currentHeight = 0;
      long pageHeight = webPage.height();
      while (currentHeight <= pageHeight) {
        int scrollCount = 0;
        while (scrollCount != 5) {
          webPage.scroll(200);
          scrollCount++;
        }

        Thread.sleep(2000);

        pageHeight = webPage.height();
        currentHeight += 5 * 200;
      }

      List<WebElement> webElements = webPage.fetchWebElements(listNode.getSelector());
      for (WebElement webElement : webElements) {
        DataRow row = collector.collect(listNode.getChildren(), webElement);
        dataRows.add(row);
      }
      reportDataTable.addAll(dataRows);
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
