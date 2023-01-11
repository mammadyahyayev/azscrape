package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.model.ReportData;

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
