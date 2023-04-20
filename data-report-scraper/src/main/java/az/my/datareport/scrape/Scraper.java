package az.my.datareport.scrape;

import az.my.datareport.tree.ReportDataTable;
import az.my.datareport.tree.Tree;

/**
 * Collects data from given source
 *
 * @see PaginationPageScraper
 */
public interface Scraper {
    /**
     * Scrapes data from webpages
     *
     * @param tree AST for scraped data
     * @return list of report datas
     */
    ReportDataTable scrape(Tree tree);
}
