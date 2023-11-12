package az.caspian.scrape.templates.pagination.item;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertThrows;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.scrape.templates.pagination.PageParameters;
import org.junit.jupiter.api.Test;

class PaginationItemVisitorTemplateTest {

  @Test
  void throwExceptionWhenRootElementIsNotLink() {
    var pageParameters =
        new PageParameters.Builder().url("https://turbo.az/autos?page=" + PAGE_SPECIFIER).build();

    var tree = new DataTree<>();
    tree.addNode(new DataNode("", ""));

    assertThrows(
        IllegalStateException.class, () -> new PaginationItemVisitorTemplate(pageParameters, tree));
  }
}
