package az.caspian.scrape;

import az.caspian.scrape.templates.Scraper;

/**
 * The {@link ScrapeFailedException} will be thrown when scraping process
 * is failed in any implementation of {@link Scraper}.
 *
 */
public class ScrapeFailedException extends RuntimeException {
  public ScrapeFailedException(String message) {
    super(message);
  }

  public ScrapeFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
