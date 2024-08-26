package az.caspian.core.template;

/**
 * Base interface for Scrape Templates.
 */
public interface ScrapeTemplate {

  /**
   * Name of the Scrape Template.
   *
   * @return a name of Scrape Template
   */
  String name();

  /**
   * Indicates a Scrape Template whether supports parallel execution or not.
   *
   * @return {@code true} if template supports parallel execution,
   * otherwise {@code false}
   */
  boolean supportsParallelExecution();

}
