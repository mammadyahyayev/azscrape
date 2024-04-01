package az.caspian.scrape.templates.multiurl;

import az.caspian.core.model.DataColumn;
import az.caspian.core.model.DataRow;
import az.caspian.core.tree.DataTable;
import az.caspian.scrape.ScrapedDataCollector;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.AbstractScrapeTemplate;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MultiUrlTemplateScraper extends AbstractScrapeTemplate<MultiUrlTemplate> {
  private static final Logger LOG = LogManager.getLogger(MultiUrlTemplateScraper.class);
  private final ScrapedDataCollector collector = new ScrapedDataCollector();

  public MultiUrlTemplateScraper() {

  }

  public MultiUrlTemplateScraper(ScrapeErrorCallback callback) {
    super(callback);
  }

  @Override
  public DataTable scrape(MultiUrlTemplate template) {
    DataTable dataTable = new DataTable();

    try (var browser = new WebBrowser()) {
      browser.open();

      var templateParameters = template.getTemplateParameters();
      Set<String> urls = templateParameters.getUrls();
      List<DataRow> dataRows = new ArrayList<>();
      for (String strUrl : urls) {
        scrapePage(browser, strUrl, template, dataRows);
      }

      Path urlSourceFilePath = templateParameters.getUrlSourceFilePath();
      if (urlSourceFilePath != null) {
        Files.readAllLines(urlSourceFilePath)
          .forEach(strUrl -> scrapePage(browser, strUrl, template, dataRows));
      }

      dataTable.addAll(dataRows);
    } catch (Exception e) {
      if (e instanceof TemplateException ex) {
        throw ex;
      }

      if (callback != null) {
        LOG.info("Callback function is executed because of failure");
        callback.handle(e.getMessage(), dataTable);
      }

      LOG.error("Exception: {}", e.getMessage());
    }

    return dataTable;
  }

  private void scrapePage(WebBrowser browser, String strUrl, MultiUrlTemplate template, List<DataRow> dataRows) {
    MultiUrlTemplateParameters templateParameters = template.getTemplateParameters();
    long delayBetweenUrls = templateParameters.getDelayBetweenUrls();

    try {
      URL url = new URL(strUrl);
      WebPage page = browser.goTo(url.toString(), delayBetweenUrls);

      var nodes = template.getTree().nodes();
      List<DataRow> collectedDataRows = collector.collectListNodes(nodes, page);
      dataRows.addAll(collectedDataRows);

      LOG.debug("Data scraped from url: {}", strUrl);
    } catch (MalformedURLException e) {
      if (templateParameters.isFailFast()) {
        LOG.error("Template failed fast because of invalid url {}", strUrl);
        throw new TemplateException("Given string %s is not valid url!".formatted(strUrl), e);
      }

      LOG.warn("{} is skipped because it isn't valid url", strUrl);
    }
  }
}
