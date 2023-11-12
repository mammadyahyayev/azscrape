package az.caspian.scrape.templates;

/**
 * Shared codes between scrapers which simplify the scraping process.
 *
 * @param <T> one of the scrape templates
 */
public abstract class AbstractScrapeTemplate<T extends ScrapeTemplate> implements Scraper<T> {
  public AbstractScrapeTemplate() {}
}
