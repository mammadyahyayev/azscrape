package az.my.datareport.service;

import az.my.datareport.model.ReportData;
import az.my.datareport.scrape.WebScraper;
import az.my.datareport.tree.AbstractTree;
import org.springframework.stereotype.Service;

@Service
public class ScraperService {

    private final WebScraper scraper;

    public ScraperService(WebScraper scraper) {
        this.scraper = scraper;
    }

    public ReportData getScrapedData(String url, AbstractTree abstractTree) {
        return scraper.scrape(url, abstractTree);
    }
}
