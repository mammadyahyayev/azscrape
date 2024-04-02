package az.caspian.scrape;

import org.openqa.selenium.*;

import java.util.List;

/**
 * Decorator of {@link WebElement}. It will eliminate of using try-catch statements in other places.
 * It will log the internal exceptions
 */
public class HtmlElement {
  private final WebElement webElement;

  public HtmlElement(WebElement webElement) {
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
}

@SuppressWarnings("unused")
class SafeWebElement implements WebElement {

  private final WebElement webElement;

  public SafeWebElement(WebElement webElement) {
    this.webElement = webElement;
  }

  @Override
  public void click() {
    webElement.click();
  }

  @Override
  public void submit() {
    webElement.submit();
  }

  @Override
  public void sendKeys(CharSequence... keysToSend) {
    webElement.sendKeys(keysToSend);
  }

  @Override
  public void clear() {
    webElement.clear();
  }

  @Override
  public String getTagName() {
    return webElement.getTagName();
  }

  @Override
  public String getDomProperty(String name) {
    return webElement.getDomProperty(name);
  }

  @Override
  public String getDomAttribute(String name) {
    return webElement.getDomAttribute(name);
  }

  @Override
  public String getAttribute(String name) {
    return webElement.getAttribute(name);
  }

  @Override
  public String getAriaRole() {
    return webElement.getAriaRole();
  }

  @Override
  public String getAccessibleName() {
    return webElement.getAccessibleName();
  }

  @Override
  public boolean isSelected() {
    return webElement.isSelected();
  }

  @Override
  public boolean isEnabled() {
    return webElement.isEnabled();
  }

  @Override
  public String getText() {
    return webElement.getText();
  }

  @Override
  public List<WebElement> findElements(By by) {
    return webElement.findElements(by);
  }

  @Override
  public WebElement findElement(By by) {
    try {
      return webElement.findElement(by);
    } catch (NoSuchElementException e) {
      // Ignore: It is highly possible that this exception will throw on web pages.
      return null;
    }
  }

  @Override
  public SearchContext getShadowRoot() {
    return webElement.getShadowRoot();
  }

  @Override
  public boolean isDisplayed() {
    return webElement.isDisplayed();
  }

  @Override
  public Point getLocation() {
    return webElement.getLocation();
  }

  @Override
  public Dimension getSize() {
    return webElement.getSize();
  }

  @Override
  public Rectangle getRect() {
    return webElement.getRect();
  }

  @Override
  public String getCssValue(String propertyName) {
    return webElement.getCssValue(propertyName);
  }

  @Override
  public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
    return webElement.getScreenshotAs(target);
  }
}
