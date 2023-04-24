package az.my.datareport.scrape;

import az.my.datareport.tree.ReportDataTable;
import az.my.datareport.tree.Tree;

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
