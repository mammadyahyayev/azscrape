package az.caspian.scrape.templates.pagination;

import az.caspian.core.messaging.Client;
import az.caspian.core.task.Task;
import az.caspian.core.template.ScrapeTemplate;
import az.caspian.core.template.SplitStrategy;
import az.caspian.core.template.Splittable;
import az.caspian.core.tree.DataTree;
import az.caspian.core.tree.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PaginationTemplate implements ScrapeTemplate, Splittable {
  private final PageParameters pageParameters;
  private final DataTree<Node> tree;

  public PaginationTemplate(PageParameters pageParameters, DataTree<Node> tree) {
    this.pageParameters = Objects.requireNonNull(pageParameters);
    this.tree = Objects.requireNonNull(tree);
  }

  public PageParameters getPageParameters() {
    return pageParameters;
  }

  public DataTree<Node> getTree() {
    return tree;
  }

  @Override
  public String name() {
    return "Pagination Template";
  }

  @Override
  public boolean supportParallelExecution() {
    return true;
  }

  @Override
  public List<Task> split(String taskName, List<Client> clients) {
    return split(taskName, clients, SplitStrategy.EQUAL);
  }

  @Override
  public List<Task> split(String taskName, List<Client> clients, SplitStrategy strategy) {
    return switch (strategy) {
      case EQUAL -> new EqualSplitStrategy().split(taskName, clients);
      case SYSTEM_POWER -> new SystemPoweredSplitStrategy().split(taskName, clients);
    };
  }

  private class EqualSplitStrategy {
    private static final int PAGE_COUNT_FOR_EACH = 3;

    public List<Task> split(String taskName, List<Client> clients) {
      int startPage = pageParameters.startPage();
      int endPage = pageParameters.endPage();
      int totalClientCount = clients.size();
      int totalPageCount = endPage - startPage + 1;
      int pageCountForEach = totalPageCount / totalClientCount;
      int remainder = totalPageCount % totalClientCount;

      if (pageCountForEach < PAGE_COUNT_FOR_EACH) {
        remainder = totalPageCount % PAGE_COUNT_FOR_EACH;
        int clientCount = (totalPageCount / PAGE_COUNT_FOR_EACH) + (remainder == 0 ? 0 : 1);
        List<Client> subClients = clients.subList(0, clientCount);
        return createTasks(taskName, subClients, startPage, PAGE_COUNT_FOR_EACH, endPage, 0);
      }

      return createTasks(taskName, clients, startPage, pageCountForEach, endPage, remainder);
    }

    private List<Task> createTasks(
      String taskName,
      Collection<Client> clients,
      int startPage,
      int pageCountForEach,
      int endPage,
      int remainder
    ) {
      List<Task> tasks = new ArrayList<>();

      for (Client client : clients) {
        int clientEndPage = startPage + (pageCountForEach - 1) + (remainder > 0 ? 1 : 0);

        if (clientEndPage > endPage) {
          clientEndPage = endPage;
        }

        var task = createTask(taskName, client, startPage, clientEndPage);
        tasks.add(task);

        startPage += pageCountForEach + (remainder > 0 ? 1 : 0);
        remainder--;
      }

      return tasks;
    }

    private Task createTask(String taskName, Client client, int startPage, int endPage) {
      var clientPageParameters = new PageParameters.Builder()
        .url(pageParameters.getPageUrlFormat())
        .pageRange(startPage, endPage)
        .delayBetweenPages(pageParameters.getDelayBetweenPages())
        .build();

      var paginationTemplate = new PaginationTemplate(clientPageParameters, tree);

      return new Task(taskName, paginationTemplate, client);
    }
  }

  private class SystemPoweredSplitStrategy {
    public List<Task> split(String taskName, Collection<Client> clients) {
      throw new UnsupportedOperationException(
        "SystemPowered split strategy isn't supported for " + PaginationTemplate.this.name() + " yet!");
    }
  }
}
