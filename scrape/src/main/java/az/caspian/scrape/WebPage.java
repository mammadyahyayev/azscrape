package az.caspian.scrape;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * A Web Page
 */
public class WebPage {
  private static final Logger LOG = LogManager.getLogger(WebPage.class);

  private WebBrowser browser;
  private final String url;
  private final boolean isConnected;

  WebPage(String url) {
    Objects.requireNonNull(url, "Webpage url cannot be null!");
    // TODO: Check given url is valid
    this.url = url;
    this.isConnected = true;
  }

  WebPage(String url, WebBrowser browser) {
    Objects.requireNonNull(url, "Webpage url cannot be null!");
    this.url = url;
    this.browser = browser;
    this.isConnected = true;
  }

  /**
   * Fetch web elements and stores them in list of strings
   *
   * @param cssSelector selector of the element
   * @return scraped elements
   */
  public String fetchElementsAsText(String cssSelector, WebElement webElement) {
    String text;
    try {
      text =
        webElement.findElements(By.cssSelector(cssSelector)).stream()
          .map(WebElement::getText)
          .collect(Collectors.joining("\n"));
    } catch (Exception e) {
      LOG.error("Unknown error happened");
      text = "";
    }

    return text;
  }

  /**
   * Fetch a web element
   *
   * @param cssSelector selector of the element
   * @return scraped element
   */
  public String fetchElementAsText(String cssSelector, WebElement webElement) {
    if (webElement == null) {
      return "";
    }

    String text = "";
    try {
      WebElement element = webElement.findElement(By.cssSelector(cssSelector));
      if (element != null) text = element.getText();
    } catch (Exception e) {
      LOG.error("Unknown error happened: {}", e.getMessage());
      text = "";
    }

    return text;
  }

  public String fetchElementAsText(String cssSelector) {
    String text;
    try {
      text = browser.findElement(By.cssSelector(cssSelector)).getText();
    } catch (Exception e) {
      LOG.error("Unknown error happened: {}", e.getMessage());
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
      elements = new ArrayList<>(browser.findElements(By.cssSelector(cssSelector)));
    } catch (Exception e) {
      LOG.error("Unknown error happened: {}", e.getMessage());
    }

    return elements;
  }

  public Optional<WebElement> fetchWebElement(String cssSelector) {
    WebElement element;
    try {
      element = browser.findElement(By.cssSelector(cssSelector));
      return Optional.of(element);
    } catch (Exception e) {
      LOG.error("Unknown error happened: {}", e.getMessage());
    }

    return Optional.empty();
  }

  /**
   * Scrolls to the end of Web Page
   */
  public void scrollToEnd() {
    browser.executeScript("return document.body.offsetHeight");
  }

  public void scroll(int scrollHeight) {
    browser.executeScript(format("window.scrollBy(0, %d)", scrollHeight));
  }

  /**
   * Returns height of the Web Page scroll from beginning to end.
   *
   * @return scroll height
   */
  public long height() {
    return (long) browser.executeScript("return document.body.scrollHeight");
  }

  /**
   * Checks whether web page opened in WebDriver, or not
   *
   * @return {@code true}, if web page opened in WebDriver, otherwise {@code false}
   */
  public boolean isConnected() {
    return isConnected;
  }

  public String getUrl() {
    return url;
  }
}
