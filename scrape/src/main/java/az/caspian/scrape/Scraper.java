package az.caspian.scrape;

import az.caspian.scrape.templates.pagination.PaginationPageScraper;
import az.caspian.core.tree.ReportDataTable;

/**
 * Collects data from given source
 *
 * @see PaginationPageScraper
 */
public interface Scraper<T> {
    /**
     * Scrapes data from webpages
     *
     * @param type scraping type
     * @return list of report data
     */
    ReportDataTable scrape(T type);
}
