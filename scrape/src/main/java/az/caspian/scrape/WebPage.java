package az.caspian.scrape;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    private final String url;
    private final boolean isConnected;

    WebPage(String url) {
        Objects.requireNonNull(url, "Webpage url cannot be null!");
        //TODO: Check given url is valid
        this.url = url;
        this.isConnected = true;
    }


    /**
     * Fetch web elements and stores them in list of strings
     *
     * @param cssSelector selector of the element
     * @return scraped elements
     */
    public List<String> fetchElementsAsText(String cssSelector) {
        List<String> elements = new ArrayList<>();
        try {
            elements = WebBrowser.DRIVER.findElements(By.cssSelector(cssSelector))
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
        List<WebElement> elements = new ArrayList<>();
        try {
            elements = new ArrayList<>(WebBrowser.DRIVER.findElements(By.cssSelector(cssSelector)));
        } catch (Exception e) {
            LOG.error("Unknown error happened: " + e);
        }

        return elements;
    }


    /**
     * Scrolls to the end of Web Page
     */
    public void scrollToEnd() {
        JavascriptExecutor js = (JavascriptExecutor) WebBrowser.DRIVER;
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
        JavascriptExecutor js = (JavascriptExecutor) WebBrowser.DRIVER;
        return (long) js.executeScript("return document.body.scrollHeight");
    }

    /**
     * Checks whether web page opened in WebDriver, or not
     *
     * @return {@code true}, if web page opened in WebDriver, otherwise {@code false}
     */
    public boolean isConnected() {
        return isConnected;
    }
}
