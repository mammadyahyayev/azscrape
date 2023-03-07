package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.DataAST;
import az.my.datareport.tree.Tree;

/**
 * Mine data from web pages
 */
public interface Scraper {
    /**
     * Scrapes data from webpages
     *
     * @param tree AST for scraped data
     * @return report data
     */
    ReportData scrape(DataAST tree);

    /**
     * Scrapes data from webpages
     *
     * @param url  a website url
     * @param tree AST for scraped data
     * @return report data
     */
    ReportData scrape(String url, Tree tree);
}
