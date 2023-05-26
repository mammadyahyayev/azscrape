package az.caspian.scrape.templates;

/**
 * Base interface for Scrape Templates.
 *
 * @see az.caspian.scrape.templates.pagination.PaginationTemplate
 * @see az.caspian.scrape.templates.scroll.ScrollablePageTemplate
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
    boolean supportParallelExecution();

}
