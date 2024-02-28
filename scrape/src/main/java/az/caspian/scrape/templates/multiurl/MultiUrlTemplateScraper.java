package az.caspian.scrape.templates.multiurl;

import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.core.tree.ListNode;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MultiUrlTemplateScraper extends AbstractScrapeTemplate<MultiUrlTemplate> {
  private static final Logger LOG = LogManager.getLogger(WebPage.class);
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

  @Override
  public DataTable scrape(MultiUrlTemplate template) {
    DataTable dataTable = new DataTable();

    try (var browser = new WebBrowser()) {
      browser.open();

      var templateParameters = template.getTemplateParameters();
      long delayBetweenUrls = templateParameters.getDelayBetweenUrls();
      Set<String> urls = templateParameters.getUrls();
      List<DataRow> dataRows = new ArrayList<>();
      for (String strUrl : urls) {
        try {
          URL url = new URL(strUrl);
          WebPage page = browser.goTo(url.toString(), delayBetweenUrls);

          var rootNode = (ListNode) template.getTree().nodes().get(0);
          List<WebElement> webElements = page.fetchWebElements(rootNode.getSelector());
          for (WebElement webElement : webElements) {
            DataRow dataRow = collector.collect(rootNode.getChildren(), webElement);
            dataRows.add(dataRow);
          }
        } catch (MalformedURLException e) {
          if (templateParameters.isFailFast()) {
            LOG.error("Template failed fast because of invalid url {}", strUrl);
            throw new TemplateException("Given string %s is not valid url!".formatted(strUrl), e);
          }

          LOG.warn("{} is skipped because it isn't valid url", strUrl);
        }
      }

      Path urlSourceFilePath = templateParameters.getUrlSourceFilePath();
      Files.readAllLines(urlSourceFilePath).forEach(strUrl -> {
        try {
          URL url = new URL(strUrl);
          WebPage page = browser.goTo(url.toString(), delayBetweenUrls);

          var rootNode = (ListNode) template.getTree().nodes().get(0);
          List<WebElement> webElements = page.fetchWebElements(rootNode.getSelector());
          for (WebElement webElement : webElements) {
            DataRow dataRow = collector.collect(rootNode.getChildren(), webElement);
            dataRows.add(dataRow);
          }
        } catch (MalformedURLException e) {
          if (templateParameters.isFailFast()) {
            LOG.error("Template failed fast because of invalid url {}", strUrl);
            throw new TemplateException("Given string %s is not valid url!".formatted(strUrl), e);
          }

          LOG.warn("{} is skipped because it isn't valid url", strUrl);
        }
      });

      dataTable.addAll(dataRows);
    } catch (Exception e) {
      if (e instanceof TemplateException ex) {
        throw ex;
      }

      LOG.error("Exception: {}", e.getMessage());
    }

    return dataTable;
  }
}
