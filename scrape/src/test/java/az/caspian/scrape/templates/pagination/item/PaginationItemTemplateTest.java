package az.caspian.scrape.templates.pagination.item;

import az.caspian.core.tree.DataNode;
import az.caspian.core.tree.DataTree;
import az.caspian.scrape.templates.pagination.PageParameters;
import org.junit.jupiter.api.Test;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaginationItemTemplateTest {

    @Test
    void throwExceptionWhenRootElementIsNotLink() {
        var node = new DataNode("test", "test");
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .build();

        assertThrows(IllegalStateException.class, () -> new PaginationItemTemplate(
                pageParameters, new DataTree<>(node)
        ));
    }
}
