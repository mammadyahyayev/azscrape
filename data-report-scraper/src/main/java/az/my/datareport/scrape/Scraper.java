package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.tree.Tree;

import java.util.List;

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
    List<ReportData> scrape(Tree tree);
}
