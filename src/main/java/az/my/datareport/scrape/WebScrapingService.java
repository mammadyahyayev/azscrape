package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.model.ReportData;
import az.my.datareport.parser.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebScrapingService {

    private final WebDriver driver;

    public WebScrapingService() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    public List<ReportData> scrape(String url, DataAST dataAST) {
        Objects.requireNonNull(dataAST);

        Assert.required(url, "url is required field");
        connectWebPage(url);

        List<ReportData> reportDataList = new ArrayList<>();
        List<DataElement> elements = dataAST.getElements();

        for (DataElement element : elements) {
            List<Object> values = fetchDateFromUrl(element.getSelector());
            ReportData reportData = new ReportData(element.getName(), values);
            reportDataList.add(reportData);
        }

        return reportDataList;
    }

    private List<Object> fetchDateFromUrl(String selector) {
        try {
            return driver.findElements(By.cssSelector(selector))
                    .stream()
                    .map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
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
