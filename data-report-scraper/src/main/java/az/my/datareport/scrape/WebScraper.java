package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import az.my.datareport.tree.*;
import az.my.datareport.utils.Assert;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebScraper implements Scraper {

    @Override
    public ReportData scrape(DataAST tree) {
        Objects.requireNonNull(tree);

        DataNode dataNode = tree.getDataNode();
        WebPage page = new WebPage(dataNode.getUrl());
        page.connect();

        List<ReportDataParent> reportDataParentList = fetchDataFromUrl(dataNode, page);

        ReportData reportData = new ReportData();
        reportData.setReportParentElements(reportDataParentList);

        page.disconnect();

        return reportData;
    }

    @Override
    public ReportData scrape(String url, Tree tree) {
        Assert.required(url, "Web page url is required!");
        Objects.requireNonNull(tree);

        WebPage page = new WebPage(url);
        page.connect();


        TempDataNode node = tree.nodes().get(0);
        List<WebElement> webElements = page.fetchWebElements(node.getAttribute().getSelector());

        List<ReportDataParent> reportDataParentList = new ArrayList<>();

        for (WebElement webElement : webElements) {
            List<ReportDataElement> children = new ArrayList<>();
            ReportDataParent parent = new ReportDataParent();
            for (int i = 0; i < node.subNodes().size(); i++) {
                TempDataNode subNode = node.getSubNode(i);
                String value = page.fetchElementAsText(subNode.getAttribute().getSelector(), webElement);
                ReportDataElement reportData = new ReportDataElement(subNode.getAttribute().getName(), value);
                children.add(reportData);
            }

            parent.setReportDataElements(children);
            reportDataParentList.add(parent);
        }

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
