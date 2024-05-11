package az.caspian.scrape.templates.pagination;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.SplitStrategy;
import az.caspian.core.tree.DataTree;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationTemplateTest {


  @ParameterizedTest
  @MethodSource("taskParameters")
  void testEqualSplitStrategy(int startPage, int endPage, int clientCount, Object[][] expectedTaskSplits) {
    var pageParameters = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageRange(startPage, endPage)
      .delayBetweenPages(3000)
      .build();

    var template = new PaginationTemplate(pageParameters, new DataTree<>());

    List<Client> clients = IntStream.range(0, clientCount)
      .mapToObj(i -> new Client())
      .toList();
    List<Task> tasks = template.split("Test", clients, SplitStrategy.EQUAL);

    tasks.forEach(task -> assertEquals("Test", task.getName()));

    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);

      ScrapeTemplate scrapeTemplate = task.getTemplate();
      if (scrapeTemplate instanceof PaginationTemplate paginationTemplate) {
        var taskPageParameters = paginationTemplate.getPageParameters();
        Object[] expectedTaskSplit = expectedTaskSplits[i];
        assertEquals(expectedTaskSplit[0], taskPageParameters.startPage());
        assertEquals(expectedTaskSplit[1], taskPageParameters.endPage());
      }
    }

  }

  private static Stream<Arguments> taskParameters() {
    return Stream.of(
      Arguments.of(1, 25, 5, new Object[][]{{1, 5}, {6, 10}, {11, 15}, {16, 20}, {21, 25}}),
      Arguments.of(1, 27, 5, new Object[][]{{1, 6}, {7, 12}, {13, 17}, {18, 22}, {23, 27}}),
      Arguments.of(1, 25, 4, new Object[][]{{1, 7}, {8, 13}, {14, 19}, {20, 25}}),
      Arguments.of(3, 20, 6, new Object[][]{{3, 5}, {6, 8}, {9, 11}, {12, 14}, {15, 17}, {18, 20}}),
      Arguments.of(3, 7, 6, new Object[][]{{3, 5}, {6, 7}}),
      Arguments.of(3, 8, 6, new Object[][]{{3, 5}, {6, 8}}),
      Arguments.of(3, 9, 6, new Object[][]{{3, 5}, {6, 8}, {9, 9}})
    );
  }

}