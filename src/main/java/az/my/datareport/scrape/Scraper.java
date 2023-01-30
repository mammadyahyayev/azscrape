package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.DataAST;

/**
 *  Mine data from web pages
 */
public interface Scraper {
    /**
     * Scrapes data from webpages
     *
     * @param dataAST AST for scraped data
     * @return report data
     */
    ReportData scrape(DataAST dataAST);
}
