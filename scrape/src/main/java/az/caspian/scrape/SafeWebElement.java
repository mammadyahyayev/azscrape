package az.caspian.scrape;

import az.caspian.core.tree.node.ElementSelector;
import az.caspian.core.tree.node.SelectorType;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Decorator of {@link WebElement}. It will eliminate of using try-catch statements in other places.
 * It will log the internal exceptions
 */
public class SafeWebElement {
  private static final Logger LOG = LoggerFactory.getLogger(SafeWebElement.class);
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
  public String getElementValue(final String cssSelector) {
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

  /**
   * Retrieves text of the element on the {@link WebPage}.
   *
   * @return text of {@link SafeWebElement}
   */
  public String getText() {
    try {
      return webElement.getText();
    } catch (Exception e) {
      LOG.error("Exception occurred while retrieving text of element, ex: {}", e.getMessage());
      return null;
    }
  }

  /**
   * Applies click action to {@link SafeWebElement} on the {@link WebPage}
   */
  public void click() {
    try {
      webElement.click();
    } catch (StaleElementReferenceException e) {
      LOG.error("Element is no longer exists on the page, ex: {}", e.getMessage());
    } catch (Exception e) {
      LOG.error("Exception occurred while clicking element on the page, ex: {}", e.getMessage());
    }
  }

  public Optional<SafeWebElement> findElement(String selector) {
    try {
      WebElement element = webElement.findElement(By.cssSelector(selector));
      return Optional.of(new SafeWebElement(element));
    } catch (NoSuchElementException e) {
      return Optional.empty();
    }
  }

  public Optional<SafeWebElement> findElement(By by) {
    try {
      WebElement element = webElement.findElement(by);
      return Optional.of(new SafeWebElement(element));
    } catch (NoSuchElementException e) {
      LOG.error("No such element exists on the page, ex: {}", e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<SafeWebElement> findElement(ElementSelector selector) {
    By by = By.cssSelector(selector.pattern());
    if (selector.selectorType() == SelectorType.XPATH) {
      by = By.xpath(selector.pattern());
    }

    return findElement(by);
  }

  public String getAttribute(String attributeName) {
    try {
      return webElement.getAttribute(attributeName);
    } catch (Exception e) {
      LOG.error("Exception occurred while retrieving attribute, ex: {}", e.getMessage());
      return "";
    }
  }
}
