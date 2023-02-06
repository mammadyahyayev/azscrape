package az.my.datareport.scrape;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A webpage
 */
public class WebPage {
    private static final Logger LOG = LogManager.getLogger(WebPage.class);

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

    /**
     * Connects to web page
     */
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

    /**
     * Close connection with web page
     */
    public void disconnect() {
        if (isConnected) {
            driver.quit();
            isConnected = false;
        }
    }

    /**
     * Fetch web elements and stores them in list of strings
     * @param cssSelector selector of the element
     * @return scraped elements
     */
    public List<String> fetchElementsAsText(String cssSelector) {
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

    /**
     * Fetch a web element
     * @param cssSelector selector of the element
     * @return scraped element
     */
    public String fetchElementAsText(String cssSelector, WebElement webElement) {
        if (!isConnected) {
            connect();
        }

        String text;
        try {
            text = webElement.findElement(By.cssSelector(cssSelector)).getText();
        } catch (Exception e) {
            LOG.error("Unknown error happened: " + e);
            text = "";
        }

        return text;
    }

    /**
     * Fetch web elements and stores them in list of strings
     * @param cssSelector selector of the element
     * @return scraped elements as WebElement
     * @see WebElement
     */
    public List<WebElement> fetchWebElements(String cssSelector) {
        if (!isConnected) {
            connect();
        }

        List<WebElement> elements = new ArrayList<>();
        try {
            elements = new ArrayList<>(driver.findElements(By.cssSelector(cssSelector)));
        } catch (Exception e) {
            LOG.error("Unknown error happened: " + e);
        }

        return elements;
    }

    /**
     *
     * @return true if the driver is connected to a web page
     */
    public boolean isConnected() {
        return isConnected;
    }
}
