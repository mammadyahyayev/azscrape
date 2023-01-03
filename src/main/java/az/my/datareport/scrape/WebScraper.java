package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.ast.DataNode;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebScraper implements Scraper {

    private static final Logger LOG = LoggerFactory.getLogger(WebScraper.class);

    @Override
    public ReportData scrape(DataAST dataAST) {
        Objects.requireNonNull(dataAST);

        DataNode dataNode = dataAST.getDataNode();
        WebPage page = new WebPage(dataNode.getUrl());
        page.connect();

        List<ReportDataElement> elements = fetchDataFromUrl(page, dataNode.getElements());

        ReportData reportData = new ReportData();
        reportData.setReportDataElements(elements);

        return reportData;
    }

    private List<ReportDataElement> fetchDataFromUrl(final WebPage page, final List<DataElement> elements) {
        List<ReportDataElement> reportDataElements = new ArrayList<>();
        elements.forEach(element -> {
            List<String> values = page.fetchElements(element.getSelector());
            ReportDataElement reportData = new ReportDataElement(element.getName(), values);
            reportDataElements.add(reportData);
        });
        return reportDataElements;
    }
}
