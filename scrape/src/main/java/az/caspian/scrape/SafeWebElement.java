package az.caspian.scrape;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Optional;

/**
 * Decorator of {@link WebElement}. It will eliminate of using try-catch statements in other places.
 * It will log the internal exceptions
 */
public class SafeWebElement {
  private final WebElement webElement;

  public SafeWebElement(WebElement webElement) {
    this.webElement = webElement;
  }

  /**
   * Searches an HTML element in web page by css selector. It will return text inside the HTML
   * element. {@code null} will be returned if the HTML element isn't found on the web page.
   *
   * @param cssSelector selector of Html Element
   * @return element's text
   */
  public String getElement(final String cssSelector) {
    try {
      WebElement element = webElement.findElement(By.cssSelector(cssSelector));
      if (element == null) {
        return null;
      }

      String text = element.getText();
      if (text == null || text.isEmpty()) {
        text = element.getAttribute("innerText");
      }

      return text;
    } catch (NoSuchElementException e) {
      // Ignore: It is highly possible that this exception will throw on web pages.
      return null;
    }
  }

  public String getText() {
    return webElement.getText();
  }

  public void click() {
    webElement.click();
  }

  public Optional<SafeWebElement> findElement(String selector) {
    try {
      WebElement element = webElement.findElement(By.cssSelector(selector));
      return Optional.of(new SafeWebElement(element));
    } catch (NoSuchElementException e) {
      return Optional.empty();
    }
  }

  public String getAttribute(String attributeName) {
    return webElement.getAttribute(attributeName);
  }
}
