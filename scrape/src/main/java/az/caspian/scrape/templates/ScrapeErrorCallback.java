package az.caspian.scrape.templates;

import az.caspian.core.tree.DataTable;

/**
 * Callback is used whenever error happens while scraping data. This is
 * useful especially user Internet Connection goes away, in that case,
 * in order not to lose the data, implement the interface.
 */
public interface ScrapeErrorCallback {

    void handle(String reason, DataTable data);

}
