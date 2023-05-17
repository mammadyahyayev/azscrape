package az.caspian.scrape;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * A Web Page
 */
public class WebPage {
    private static final Logger LOG = LogManager.getLogger(WebPage.class);

    private static WebDriver driver;
    private final String url;
    private boolean isConnected;
    private final boolean keepOpen;

    WebPage(String url, WebDriver driver) {
        this.url = url;
        this.driver = driver;

        this.keepOpen = false;
        this.isConnected = true;
    }

    public WebPage(String url, boolean keepOpen) {
        Objects.requireNonNull(url, "Webpage url cannot be null!");
        //TODO: Check given url is valid
        this.url = url;
        this.keepOpen = keepOpen;
    }

    /**
     * Connects to a web page
     */
    public void connect() {
        try {
            driver.get(url);
            isConnected = true;
        } catch (Exception e) {
            LOG.error("Couldn't connect to webpage with url: [ " + url + " ]");
            disconnect();
            throw new InternetConnectionException("Failed to connect to webpage, check your internet connection!", e);
        }
    }

    /**
     * Connects to a web page with delay. It is useful
     * when connecting multiple Web page at the same session.
     *
     * @param delayInMs delay in milliseconds
     */
    public void connectWithDelay(int delayInMs) {
        try {
            Thread.sleep(delayInMs);
            connect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close connection with web page
     */
    public void disconnect() {
        if (isConnected && !keepOpen) {
            driver.quit();
            isConnected = false;
        }
    }

    /**
     * Fetch web elements and stores them in list of strings
     *
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
     *
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
     *
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
     * Checks whether driver is connected
     *
     * @return true if the driver is connected to a web page
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Scrolls to the end of Web Page
     */
    public void scrollToEnd() {
        if (!isConnected) {
            return;
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        long amount = (long) js.executeScript("return document.body.offsetHeight");

        js.executeScript(format("window.scrollBy(0, %d)", amount));
    }

    /**
     * Returns height of the Web Page scroll from beginning
     * to end.
     *
     * @return scroll height
     */
    public long height() {
        if (!isConnected) {
            return 0;
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (long) js.executeScript("return document.body.scrollHeight");
    }
}
