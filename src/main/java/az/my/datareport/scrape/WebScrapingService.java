package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.model.ReportData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WebScrapingService {

    private final WebDriver driver;

    public WebScrapingService() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    public List<ReportData> scrape(String url, DataAST dataAST) {
        driver.get(url);

        List<ReportData> reportDataList = new ArrayList<>();
        List<DataElement> elements = dataAST.getElements();

        for (DataElement element : elements) {
            List<Object> values = driver.findElements(By.cssSelector(element.getSelector()))
                    .stream()
                    .map(WebElement::getText).collect(Collectors.toList());

            ReportData reportData = new ReportData(element.getName(), values);
            reportDataList.add(reportData);
        }

        return reportDataList;
    }

}
