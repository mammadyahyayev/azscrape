package az.my.datareport.scrape;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebPage {
    private static final Logger LOG = LoggerFactory.getLogger(WebPage.class);

    private static final WebDriver driver;
    private final String url;
    private boolean isConnected;

    static {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setLogLevel(ChromeDriverLogLevel.OFF);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    public WebPage(String url) {
        Objects.requireNonNull(url, "Webpage url cannot be null!");
        //TODO: Check given url is valid
        this.url = url;
    }

    public void connect() {
        try {
            driver.get(url);
            isConnected = true;
        } catch (Exception e) {
            LOG.error("Couldn't connect to webpage with url: [ " + url + " ]");
            disconnect();
            throw new InternetConnectionException("Problem to connect to webpage, check your internet connection!", e);
        }
    }

    public void disconnect() {
        if (isConnected) {
            driver.quit();
            isConnected = false;
        }
    }

    public List<String> fetchElements(String cssSelector) {
        if (!isConnected) {
            connect();
        }

        List<String> elements = new ArrayList<>();
        try {
            elements = driver.findElements(By.cssSelector(cssSelector))
                    .stream()
                    .map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Unknown error happened");
        }

        return elements;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
