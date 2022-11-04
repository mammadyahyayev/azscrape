package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.ast.DataNode;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportDataElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebScraper implements Scraper {

    private static final Logger LOG = LoggerFactory.getLogger(WebScraper.class);

    private final WebDriver driver;

    public WebScraper() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Override
    public List<ReportData> scrape(DataAST dataAST) {
        Objects.requireNonNull(dataAST);
        List<ReportData> reportDataList = new ArrayList<>();

        List<DataNode> dataNodes = dataAST.getDataNodes();
        for (DataNode dataNode : dataNodes) {
            connectWebPage(dataNode.getUrl());
            List<ReportDataElement> elements = fetchDataFromUrl(dataNode.getElements());

            ReportData reportData = new ReportData();
            reportData.setUrl(dataNode.getUrl());
            reportData.setReportDataElements(elements);
            reportDataList.add(reportData);
        }

        return reportDataList;
    }

    private List<ReportDataElement> fetchDataFromUrl(List<DataElement> elements) {
        List<ReportDataElement> reportDataElements = new ArrayList<>();
        try {
            elements.forEach(element -> {
                List<String> values = driver.findElements(By.cssSelector(element.getSelector()))
                        .stream()
                        .map(WebElement::getText).collect(Collectors.toList());
                ReportDataElement reportData = new ReportDataElement(element.getName(), values);
                reportDataElements.add(reportData);
            });

            return reportDataElements;
        } catch (Exception e) {
            LOG.error("Unknown problem happened: " + e);
            throw new RuntimeException(e); //TODO: replace this with meaningful message and don't throw RuntimeException
        }
    }

    private void connectWebPage(String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            throw new RuntimeException("Unknown error happened, check your internet connection!", e);
        }
    }
}
