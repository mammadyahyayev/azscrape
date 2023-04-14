package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.AbstractTree;

/**
 * Collects data from given source
 *
 * @see WebScraper
 */
public interface Scraper {
    /**
     * Scrapes data from webpages
     *
     * @param url          a website url
     * @param abstractTree AST for scraped data
     * @return report data
     */
    ReportData scrape(String url, AbstractTree abstractTree);


    /**
     * Scrapes data from webpages
     *
     * @param url          a website url
     * @param abstractTree AST for scraped data
     * @param keepOpen     if value is true, then driver of Web Browser will be open,
     *                     otherwise it will be closed
     * @return report data
     */
    ReportData scrape(String url, AbstractTree abstractTree, boolean keepOpen);

}
