package az.caspian.scrape.templates;

import az.caspian.core.tree.ReportDataTable;
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
     * @param type scraping type
     * @return list of report data
     */
    ReportDataTable scrape(T type);
}
