package az.caspian.scrape.templates.multiurl;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.SplitStrategy;
import az.caspian.core.template.Splittable;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MultiUrlTemplate implements ScrapeTemplate, Splittable {
  private static final Logger LOG = LogManager.getLogger(MultiUrlTemplate.class);

  private final MultiUrlTemplateParameters templateParameters;
  private final DataTree<Node> tree;

  public MultiUrlTemplate(MultiUrlTemplateParameters templateParameters, DataTree<Node> tree) {
    this.templateParameters = templateParameters;
    this.tree = tree;
  }

  public MultiUrlTemplateParameters getTemplateParameters() {
    return templateParameters;
  }

  public DataTree<Node> getTree() {
    return tree;
  }

  @Override
  public String name() {
    return "MultiUrlTemplate";
  }

  @Override
  public boolean supportParallelExecution() {
    return false;
  }

  @Override
  public List<Task> split(String taskName, List<Client> clients) {
    return split(taskName, clients, SplitStrategy.EQUAL);
  }

  @Override
  public List<Task> split(String taskName, List<Client> clients, SplitStrategy strategy) {
    return switch (strategy) {
      case EQUAL -> new MultiUrlTemplate.EqualSplitStrategy().split(taskName, clients);
      case SYSTEM_POWER -> new MultiUrlTemplate.SystemPoweredSplitStrategy().split(taskName, clients);
    };
  }


  private class EqualSplitStrategy {
    public List<Task> split(String taskName, List<Client> clients) {
      List<Task> tasks = new ArrayList<>();
      if (clients.isEmpty()) {
        return tasks;
      }
      List<String> allUrls = new ArrayList<>(getAllUrls());

      int urlsCount = allUrls.size();
      int totalClientsCount = clients.size();
      int urlCountForEach = urlsCount / totalClientsCount;
      int remainder = urlsCount % totalClientsCount;

      int currentIndex = 0;
      for (Client client : clients) {
        int clientUrlsCount = urlCountForEach + (remainder > 0 ? 1 : 0);

        List<String> urls = allUrls.subList(currentIndex, currentIndex + clientUrlsCount);

        var clientTemplateParameters = new MultiUrlTemplateParameters.Builder()
          .urls(new HashSet<>(urls))
          .delayBetweenUrls(templateParameters.getDelayBetweenUrls(), TimeUnit.MILLISECONDS)
          .build();

        var template = new MultiUrlTemplate(clientTemplateParameters, tree);
        tasks.add(new Task(taskName, template, client));

        currentIndex += urlCountForEach + (remainder > 0 ? 1 : 0);
        remainder--;
      }

      return tasks;
    }

    private Set<String> getAllUrls() {
      Set<String> urls = templateParameters.getUrls();
      if (urls == null) {
        urls = new HashSet<>();
      }

      Path urlSourceFilePath = templateParameters.getUrlSourceFilePath();
      try (var bufferedReader = new BufferedReader(new FileReader(urlSourceFilePath.toFile()))) {
        String url;
        while ((url = bufferedReader.readLine()) != null) {
          urls.add(url);
        }
      } catch (IOException e) {
        LOG.error("Failed to read from {}, Exception: {}", urlSourceFilePath, e.getMessage());
      }

      return urls;
    }
  }

  public class SystemPoweredSplitStrategy {
    public List<Task> split(String taskName, List<Client> clients) {
      throw new UnsupportedOperationException(
        "Split strategy based on system power isn't supported for this template yet!");
    }
  }
}
