package az.caspian.core.template;

import az.caspian.core.tree.DataTable;

public interface TemplateExecutor {

  DataTable executeTemplate(ScrapeTemplate template);
}
