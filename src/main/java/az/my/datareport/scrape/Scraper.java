package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.model.ReportData;

public interface Scraper {
    ReportData scrape(DataAST dataAST);
}
