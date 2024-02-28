package az.caspian.scrape.templates;

import az.caspian.core.tree.DataTable;
import az.caspian.scrape.templates.pagination.PaginationPageScraper;

/**
 * Collects data from given source
 *
 * @see PaginationPageScraper
 */
public interface Scraper<T extends ScrapeTemplate> {
  /**
   * Scrapes data from webpages
   *
   * @param template scraping template
   * @return list of report data
   */
  DataTable scrape(T template);
}
