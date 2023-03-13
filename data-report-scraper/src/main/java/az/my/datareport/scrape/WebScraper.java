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

/**
 * Collects data from Web pages
 */
public class WebScraper implements Scraper {

    @Deprecated
    @Override
    public ReportData scrape(DataAST tree) {
        Objects.requireNonNull(tree);

        TempDataNode tempDataNode = tree.getDataNode();
        WebPage page = new WebPage(tempDataNode.getUrl(), false);
        page.connect();

        List<ReportDataParent> reportDataParentList = fetchDataFromUrl(tempDataNode, page);

        ReportData reportData = new ReportData();
        reportData.setReportParentElements(reportDataParentList);

        page.disconnect();

        return reportData;
    }

    @Override
    public ReportData scrape(String url, Tree tree) {
        return scrape(url, tree, false);
    }

    @Override
    public ReportData scrape(String url, Tree tree, boolean keepOpen) {
        Assert.required(url, "Web page url is required!");
        Objects.requireNonNull(tree);

        WebPage page = new WebPage(url, keepOpen);
        page.connect();


        DataNode node = tree.nodes().get(0);
        List<WebElement> webElements = page.fetchWebElements(node.getAttribute().getSelector());

        List<ReportDataParent> reportDataParentList = new ArrayList<>();

        for (WebElement webElement : webElements) {
            List<ReportDataElement> children = new ArrayList<>();
            ReportDataParent parent = new ReportDataParent();
            for (int i = 0; i < node.subNodes().size(); i++) {
                DataNode subNode = node.getSubNode(i);
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

    private List<ReportDataParent> fetchDataFromUrl(TempDataNode tempDataNode, WebPage page) {
        List<ReportDataParent> reportDataParentList = new ArrayList<>();

        List<WebElement> webElements = page.fetchWebElements(tempDataNode.getElement().getSelector());
        for (WebElement webElement : webElements) {
            List<ReportDataElement> children = new ArrayList<>();
            ReportDataParent parent = new ReportDataParent();
            for (DataElement child : tempDataNode.getElement().getChildren()) {
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
