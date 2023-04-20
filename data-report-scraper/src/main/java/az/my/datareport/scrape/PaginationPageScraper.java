package az.my.datareport.scrape;

import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import az.my.datareport.model.ReportDataParent;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.PaginationTree;
import az.my.datareport.tree.Tree;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PaginationPageScraper implements Scraper {
    @Override
    public List<ReportData> scrape(Tree tree) {
        if (!(tree instanceof PaginationTree)) {
            throw new IllegalArgumentException("tree type must be PaginationTree");
        }

        PaginationTree paginationTree = (PaginationTree) tree;

        List<ReportData> reportDataList = new ArrayList<>();

        var pageParameters = paginationTree.getPageParameters();
        for (int i = pageParameters.getMinPage(); i < pageParameters.getMaxPage(); i++) {
            String url = pageParameters.getPageUrl() + "&p=" + i; //TODO: Change this

            WebPage page = new WebPage(url, true);
            page.connectWithDelay(pageParameters.getDelayBetweenPages());

            List<ReportDataParent> reportDataParentList = fetchWebElements(page, paginationTree.nodes());
            ReportData reportData = new ReportData();
            reportData.setReportParentElements(reportDataParentList);
            reportDataList.add(reportData);
        }

        return reportDataList;
    }

    private List<ReportDataParent> fetchWebElements(WebPage page, List<DataNode> nodes) {
        List<ReportDataParent> reportDataParentList = new ArrayList<>();

        for (DataNode node : nodes) {
            List<WebElement> webElements = page.fetchWebElements(node.getAttribute().getSelector());

            if (node.hasSubNode()) {
                for (WebElement element : webElements) {
                    List<ReportDataElement> children = new ArrayList<>();
                    var parent = new ReportDataParent();
                    for (DataNode subNode : node.subNodes()) {
                        String value = page.fetchElementAsText(subNode.getAttribute().getSelector(), element);
                        var reportDataElement = new ReportDataElement(subNode.getAttribute().getName(), value);
                        children.add(reportDataElement);
                    }

                    parent.setReportDataElements(children);
                    reportDataParentList.add(parent);
                }
            }
        }

        return reportDataParentList;
    }
}
