package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.model.ReportData;

import java.util.List;

public interface Scraper {
    List<ReportData> scrape(DataAST dataAST);
}
