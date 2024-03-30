package az.caspian.scrape.templates.multiurl;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.SplitStrategy;
import az.caspian.core.tree.DataTree;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MultiUrlTemplateTest {

  @ParameterizedTest
  @MethodSource("taskParameters")
  void testEqualSplitStrategy(int totalUrlCount, int clientCount, int expectedLeastUrlCountForEachClient) throws IOException {
    Path filePath = createFileWithFakeUrls(0, totalUrlCount);

    var templateParameters = new MultiUrlTemplateParameters.Builder()
      .urlSource(filePath)
      .delayBetweenUrls(5, TimeUnit.SECONDS)
      .build();

    var template = new MultiUrlTemplate(templateParameters, new DataTree<>());

    List<Client> clients = IntStream.range(0, clientCount)
      .mapToObj(i -> new Client())
      .toList();

    List<Task> tasks = template.split("Test", clients, SplitStrategy.EQUAL);

    assertEquals(clientCount, tasks.size());
    for (Task task : tasks) {
      ScrapeTemplate scrapeTemplate = task.getTemplate();
      assertInstanceOf(MultiUrlTemplate.class, scrapeTemplate);

      var multiUrlTemplate = (MultiUrlTemplate) scrapeTemplate;
      var multiUrlTemplateParameters = multiUrlTemplate.getTemplateParameters();
      assertNull(multiUrlTemplateParameters.getUrlSourceFilePath());

      assertTrue(multiUrlTemplateParameters.getUrls().size() >= expectedLeastUrlCountForEachClient);
    }
  }

  private Path createFileWithFakeUrls(int beginIndex, int count) throws IOException {
    File tempFile = File.createTempFile("urls", ".txt");

    Set<String> urls = createFakeUrls(beginIndex, count);
    Path tempFilePath = tempFile.toPath();
    try (var bufferedWriter = Files.newBufferedWriter(tempFilePath)) {
      for (String url : urls) {
        bufferedWriter.write(url + System.lineSeparator());
      }
    } catch (IOException e) {
      // ignore
    }


    return tempFilePath;
  }

  private Set<String> createFakeUrls(int beginIndex, int count) {
    return IntStream.range(beginIndex, count + 1)
      .mapToObj("https://www.example%d.com"::formatted)
      .collect(Collectors.toSet());
  }

  private static Stream<Arguments> taskParameters() {
    return Stream.of(
      Arguments.of(99, 10, 9),
      Arguments.of(101, 10, 10),
      Arguments.of(102, 10, 10),
      Arguments.of(110, 10, 11),
      Arguments.of(110, 1, 110),
      Arguments.of(110, 0, 0)
    );
  }

}