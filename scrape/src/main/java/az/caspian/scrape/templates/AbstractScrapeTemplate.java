package az.caspian.scrape.templates;

import az.caspian.core.template.ScrapeTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Shared codes between scrapers which simplify the scraping process.
 *
 * @param <T> one of the scrape templates
 */
public abstract class AbstractScrapeTemplate<T extends ScrapeTemplate> implements Scraper<T> {
  protected static final Logger LOG = LogManager.getLogger(AbstractScrapeTemplate.class);

  protected ScrapeErrorCallback callback;

  public AbstractScrapeTemplate() {
    LOG.warn("""
      It is recommended to create callback method because when failure happens it will return
      collected data until failure.
      """);
  }

  protected AbstractScrapeTemplate(ScrapeErrorCallback callback) {
    this.callback = callback;
  }
}
