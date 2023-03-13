package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.Tree;

/**
 * Collects data from given source
 *
 * @see WebScraper
 */
public interface Scraper {
    /**
     * Scrapes data from webpages
     *
     * @param url  a website url
     * @param tree AST for scraped data
     * @return report data
     */
    ReportData scrape(String url, Tree tree);


    /**
     * Scrapes data from webpages
     *
     * @param url      a website url
     * @param tree     AST for scraped data
     * @param keepOpen if value is true, then driver of Web Browser will be open,
     *                 otherwise it will be closed
     * @return report data
     */
    ReportData scrape(String url, Tree tree, boolean keepOpen);

}
