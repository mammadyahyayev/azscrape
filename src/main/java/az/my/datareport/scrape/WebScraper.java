package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.ast.DataNode;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebScraper implements Scraper {

    private static final Logger LOG = LogManager.getLogger(WebScraper.class);

    @Override
    public ReportData scrape(DataAST dataAST) {
        Objects.requireNonNull(dataAST);

        DataNode dataNode = dataAST.getDataNode();
        WebPage page = new WebPage(dataNode.getUrl());
        page.connect();

        List<ReportDataParent> reportDataParentList = fetchDataFromUrl(dataNode, page);

        ReportData reportData = new ReportData();
        reportData.setReportParentElements(reportDataParentList);

        page.disconnect();

        return reportData;
    }

    private List<ReportDataParent> fetchDataFromUrl(DataNode dataNode, WebPage page) {
        List<ReportDataParent> reportDataParentList = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(dataNode.getElement().getSelector());
        for (WebElement webElement : webElements) {
            List<ReportDataElement> children = new ArrayList<>();
            ReportDataParent parent = new ReportDataParent();
            for (DataElement child : dataNode.getElement().getChildren()) {
                String value = page.fetchElementAsText(child.getSelector(), webElement);
                ReportDataElement reportData = new ReportDataElement(child.getName(), value);
                children.add(reportData);
            }

            parent.setReportDataElements(children);
            reportDataParentList.add(parent);
        }

        return reportDataParentList;
    }

}
