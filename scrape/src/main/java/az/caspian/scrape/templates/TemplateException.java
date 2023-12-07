package az.caspian.scrape.templates;

/** The RuntimeException that will be thrown when Scrape Templates configured incorrectly. */
public class TemplateException extends RuntimeException {
  public TemplateException() {
    super();
  }

  public TemplateException(String message) {
    super(message);
  }

  public TemplateException(String message, Throwable cause) {
    super(message, cause);
  }

  public TemplateException(Throwable cause) {
    super(cause);
  }
}
