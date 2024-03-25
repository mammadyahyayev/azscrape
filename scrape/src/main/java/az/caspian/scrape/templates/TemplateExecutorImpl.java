package az.caspian.scrape.templates;

import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.TemplateExecutor;
import az.caspian.core.tree.DataTable;
import az.caspian.scrape.templates.multiurl.MultiUrlTemplate;
import az.caspian.scrape.templates.multiurl.MultiUrlTemplateScraper;
import az.caspian.scrape.templates.pagination.PaginationPageScraper;
import az.caspian.scrape.templates.pagination.PaginationTemplate;
import az.caspian.scrape.templates.pagination.item.PaginationItemVisitorScraper;
import az.caspian.scrape.templates.pagination.item.PaginationItemVisitorTemplate;
import az.caspian.scrape.templates.scroll.ScrollablePageScraper;
import az.caspian.scrape.templates.scroll.ScrollablePageTemplate;

public class TemplateExecutorImpl implements TemplateExecutor {
  @Override
  public DataTable executeTemplate(ScrapeTemplate template) {
    if (template instanceof PaginationItemVisitorTemplate paginationItemVisitorTemplate) {
      var paginationItemVisitorScraper = new PaginationItemVisitorScraper();
      return paginationItemVisitorScraper.scrape(paginationItemVisitorTemplate);
    }
    else if (template instanceof PaginationTemplate paginationTemplate) {
      var paginationPageScraper = new PaginationPageScraper();
      return paginationPageScraper.scrape(paginationTemplate);
    }
    else if (template instanceof ScrollablePageTemplate scrollablePageTemplate) {
      var scrollablePageScraper = new ScrollablePageScraper();
      return scrollablePageScraper.scrape(scrollablePageTemplate);
    }
    else if (template instanceof MultiUrlTemplate multiUrlTemplate) {
      var multiUrlTemplateScraper = new MultiUrlTemplateScraper();
      return multiUrlTemplateScraper.scrape(multiUrlTemplate);
    }

    return null;
  }
}
