package az.caspian.app;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.io.DefaultFileSystem;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.task.Task;
import az.caspian.core.tree.*;
import az.caspian.core.tree.node.*;
import az.caspian.export.csv.CsvExporter;
import az.caspian.export.excel.ExcelExporter;
import az.caspian.export.Exporter;
import az.caspian.scrape.WebBrowser;
import az.caspian.scrape.WebPage;
import az.caspian.scrape.templates.ScrapeErrorCallback;
import az.caspian.scrape.templates.Scraper;
import az.caspian.scrape.templates.multiurl.MultiUrlTemplate;
import az.caspian.scrape.templates.multiurl.MultiUrlTemplateParameters;
import az.caspian.scrape.templates.multiurl.MultiUrlTemplateScraper;
import az.caspian.scrape.templates.pagination.PageParameters;
import az.caspian.scrape.templates.pagination.PaginationPageScraper;
import az.caspian.scrape.templates.pagination.PaginationTemplate;
import az.caspian.scrape.templates.pagination.item.PaginationItemVisitorScraper;
import az.caspian.scrape.templates.pagination.item.PaginationItemVisitorTemplate;
import az.caspian.scrape.templates.scroll.ScrollablePageParameters;
import az.caspian.scrape.templates.scroll.ScrollablePageScraper;
import az.caspian.scrape.templates.scroll.ScrollablePageTemplate;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.*;

class AzScrapeApplicationTest {
  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testExporting() {
    var pageParameters = new PageParameters.Builder()
      .url(
        "https://bina.az/baki/alqi-satqi/menziller/yeni-tikili/1-otaqli?page="
          + PAGE_SPECIFIER)
      .pageRange(1, 3)
      .delayBetweenPages(6000)
      .build();

    var listNode = new ListNode("repoItem", ".items-i");
    var location = new DataNode("location", ".card_params .location");
    var price = new DataNode("price", ".card_params .price-val");
    var currency = new DataNode("currency", ".card_params .price-cur");
    var roomCount = new DataNode("room count", ".card_params .name > li:nth-child(1)");
    var area = new DataNode("area", ".card_params .name > li:nth-child(2)");
    var floor = new DataNode("floor", ".card_params .name > li:nth-child(3)");

    listNode.addChild(location);
    listNode.addChild(price);
    listNode.addChild(currency);
    listNode.addChild(roomCount);
    listNode.addChild(area);
    listNode.addChild(floor);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    PaginationTemplate paginationTemplate = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
    DataTable table = scraper.scrape(paginationTemplate);

    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile =
      new DataFile.Builder()
        .filename("one_room_apartment")
        .fileType(FileType.EXCEL)
        .storeAt(Path.of("C:/Users/User/Desktop").toString())
        .build();

    excelExporter.export(dataFile, table);

    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testContactHome() {
    var pageParameters = new PageParameters.Builder()
      .url("https://kontakt.az/telefonlar/mobil-telefonlar/page/" + PAGE_SPECIFIER)
      .pageRange(1, 5)
      .delayBetweenPages(3000)
      .build();

    var listNode = new ListNode("repoItem", ".cart-item");
    var phone = new DataNode("name", ".cart-body-top .name > a");
    var price = new DataNode("price", ".cart-footer > p .nprice");
    var currency = new DataNode("currency", ".cart-footer > p .nprice + small");

    listNode.addChild(phone);
    listNode.addChild(price);
    listNode.addChild(currency);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    PaginationTemplate paginationTemplate = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
    DataTable table = scraper.scrape(paginationTemplate);

    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile = new DataFile.Builder()
      .filename("smartphones")
      .fileType(FileType.EXCEL)
      .storeAt(System.getProperty("user.home") + File.separator + "export-file")
      .build();

    excelExporter.export(dataFile, table);
    assertNotNull(dataFile.getFileAbsolutePath());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testScrollablePageTurboAz() {
    var pageParameters = new ScrollablePageParameters.Builder()
      .url("https://turbo.az/")
      .build();

    var listNode = new ListNode("wrapper", ".products-i");
    var car = new DataNode("car", ".products-i__name");
    var price = new DataNode("price", ".products-i__price .product-price");
    var details = new DataNode("details", ".products-i__attributes");
    listNode.addChild(car);
    listNode.addChild(price);
    listNode.addChild(details);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    var template = new ScrollablePageTemplate(pageParameters, tree);

    Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
    DataTable table = scraper.scrape(template);

    var excelExporter = new ExcelExporter();

    var reportFile = new DataFile.Builder()
      .filename("turbo_az")
      .storeAt(Path.of("C:/Users/User/Desktop").toString())
      .fileType(FileType.EXCEL)
      .build();

    excelExporter.export(reportFile, table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testTurboAzWithPaginationTemplate() {
    var pageParameters = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageRange(1, 416)
      .delayBetweenPages(3000)
      .build();

    var listNode = new ListNode("wrapper", ".products-i");
    var car = new DataNode("car", ".products-i__name");
    var price = new DataNode("price", ".products-i__price .product-price");
    var details = new DataNode("details", ".products-i__attributes");

    listNode.addChild(car);
    listNode.addChild(price);
    listNode.addChild(details);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
    DataTable table = scraper.scrape(template);

    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile =
      new DataFile.Builder()
        .filename("turbo_az")
        .fileType(FileType.EXCEL)
        .build();

    excelExporter.export(dataFile, table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testCallbackCalledWhenInternetConnectionGone() {
    var pageParameters =
      new PageParameters.Builder()
        .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
        .pageRange(1, 416)
        .delayBetweenPages(3000)
        .build();

    var listNode = new ListNode("listNode", ".products-i");
    var car = new DataNode("car", ".products-i__name");
    var price = new DataNode("price", ".products-i__price .product-price");
    var details = new DataNode("details", ".products-i__attributes");

    listNode.addChild(car);
    listNode.addChild(price);
    listNode.addChild(details);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper(this::callback);
    assertThrows(Exception.class, () -> scraper.scrape(template));
  }

  void callback(String message, DataTable data) {
    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile =
      new DataFile.Builder()
        .filename("turbo_az_with_callback")
        .fileType(FileType.EXCEL)
        .build();

    excelExporter.export(dataFile, data);
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testPaginationItemVisitorTemplate() {
    var pageParameters =
      new PageParameters.Builder()
        .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
        .pageNum(1)
        .delayBetweenPages(3000)
        .build();

    var link = new ListNode("link", ".products-i__link");
    var carNode = new DataNode("car", ".product-title");
    var price = new DataNode("price", ".product-price > div:first-child");
    var advertisementId = new DataNode("advertisement number", ".product-actions__id");
    var description = new DataNode("description", ".product-description__content");
    var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
    var viewCount = new DataNode("view count", ".product-statistics__i:last-child");

    var propertyWrapper = new ListNode("wrapper", ".product-properties__i");
    var properties =
      new KeyValueNode(".product-properties__i-name", ".product-properties__i-value");
    propertyWrapper.addChild(properties);

    link.addChild(carNode);
    link.addChild(price);
    link.addChild(advertisementId);
    link.addChild(description);
    link.addChild(updateTime);
    link.addChild(viewCount);
    link.addChild(propertyWrapper);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(link);

    PaginationItemVisitorTemplate template =
      new PaginationItemVisitorTemplate(pageParameters, tree);

    PaginationItemVisitorScraper scraper = new PaginationItemVisitorScraper();
    DataTable table = scraper.scrape(template);

    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile =
      new DataFile.Builder()
        .filename("turbo_az")
        .storeAt(Path.of("C:/Users/User/Desktop").toString())
        .fileType(FileType.EXCEL)
        .build();

    excelExporter.export(dataFile, table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testPaginationItemVisitorTemplateWithExportToCsv() {
    var pageParameters = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageNum(1)
      .delayBetweenPages(3000)
      .build();

    var link = new ListNode("link", ".products-i__link");
    var carNode = new DataNode("car", ".product-title");
    var price = new DataNode("price", ".product-price > div:first-child");
    var advertisementId = new DataNode("advertisement number", ".product-actions__id");
    var description = new DataNode("description", ".product-description__content");
    var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
    var viewCount = new DataNode("view count", ".product-statistics__i:last-child");

    var propertyWrapper = new ListNode("wrapper", ".product-properties__i");
    var properties =
      new KeyValueNode(".product-properties__i-name", ".product-properties__i-value");
    propertyWrapper.addChild(properties);

    link.addChild(carNode);
    link.addChild(price);
    link.addChild(advertisementId);
    link.addChild(description);
    link.addChild(updateTime);
    link.addChild(viewCount);
    link.addChild(propertyWrapper);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(link);

    var template = new PaginationItemVisitorTemplate(pageParameters, tree);

    var scraper = new PaginationItemVisitorScraper();
    DataTable table = scraper.scrape(template);

    Exporter csvExporter = new CsvExporter();

    //TODO: We can create multiple DataFileFormat such as CsvDataFile, ExcelDataFile. Each file will has own
    // parameters. And Builder will return exact fileFormat with its own parameters, after fileType()
    DataFile dataFile = new DataFile.Builder()
      .filename("turbo_az")
      .storeAt(Path.of("C:/Users/User/Desktop").toString())
      .fileType(FileType.CSV)
      .build();

    csvExporter.export(dataFile, table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  // TODO: 2509 - bina.az https://bina.az/alqi-satqi?page=2509

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testMultipleChromeInstanceWithThreads() throws InterruptedException, ExecutionException {
    var pageParameters1 = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageNum(1)
      .delayBetweenPages(3000)
      .build();

    var pageParameters2 = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageNum(2)
      .delayBetweenPages(3000)
      .build();

    var pageParameters3 = new PageParameters.Builder()
      .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
      .pageNum(3)
      .delayBetweenPages(3000)
      .build();

    var link = new ListNode("link", ".products-i__link");
    var carNode = new DataNode("car", ".product-title");
    var price = new DataNode("price", ".product-price > div:first-child");
    var advertisementId = new DataNode("advertisement number", ".product-actions__id");
    var description = new DataNode("description", ".product-description__content");
    var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
    var viewCount = new DataNode("view count", ".product-statistics__i:last-child");

    var propertyWrapper = new ListNode("wrapper", ".product-properties__i");
    var properties =
      new KeyValueNode(".product-properties__i-name", ".product-properties__i-value");
    propertyWrapper.addChild(properties);

    link.addChild(carNode);
    link.addChild(price);
    link.addChild(advertisementId);
    link.addChild(description);
    link.addChild(updateTime);
    link.addChild(viewCount);
    link.addChild(propertyWrapper);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(link);

    var template1 = new PaginationItemVisitorTemplate(pageParameters1, tree);
    var template2 = new PaginationItemVisitorTemplate(pageParameters2, tree);
    var template3 = new PaginationItemVisitorTemplate(pageParameters3, tree);

    Task task1 = new Task("#1", "turboaz-scraping", template1, null);
    Task task2 = new Task("#2", "turboaz-scraping", template2, null);
    Task task3 = new Task("#3", "turboaz-scraping", template3, null);

    TaskExecutor executor1 = new TaskExecutor(task1);
    TaskExecutor executor2 = new TaskExecutor(task2);
    TaskExecutor executor3 = new TaskExecutor(task3);

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    List<Future<DataTable>> futures = executorService.invokeAll(List.of(executor1, executor2, executor3));

    DataTable table = new DataTable();
    for (Future<DataTable> future : futures) {
      DataTable dataTable = future.get();
      table.addAll(dataTable.rows());
    }

    Exporter csvExporter = new CsvExporter();

    DataFile dataFile = new DataFile.Builder()
      .filename("turbo_az")
      .storeAt(Path.of("C:/Users/Admin/Desktop").toString())
      .fileType(FileType.CSV)
      .build();

    csvExporter.export(dataFile, table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  static class TaskExecutor implements Callable<DataTable> {
    private final Task task;

    TaskExecutor(Task task) {
      this.task = task;
    }

    @Override
    public DataTable call() throws Exception {
      PaginationItemVisitorTemplate template = (PaginationItemVisitorTemplate) task.getTemplate();
      var scraper = new PaginationItemVisitorScraper();
      DataTable table = scraper.scrape(template);
      System.out.printf("""
        Task %s, completed its job and it collects %d rows.\s
        """, task.getId(), table.rows().size());
      return table;
    }
  }


  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testScrapingComputerScienceBooks() throws IOException {
    /*
      Book urls: .a-size-mini > .a-link-normal
      Page url: https://www.amazon.com/s?k=computer+science&rh=n%3A283155%2Cn%3A3839&dc&ds=v1%3AG6CsZ387yhcKjK0fm9lyJjv2pP2JXDVjX43u7UMiX7s&qid=1711649858&rnid=2941120011&ref=sr_nr_n_2
      Pagination Button Element: .s-pagination-next
     */

    //TODO: Do following urls tomorrow
    // https://www.amazon.com/s?k=computer+science&i=stripbooks-intl-ship&rh=n%3A283155%2Cn%3A75&s=review-count-rank&dc&crid=2UU2E05VLYK8A&qid=1711658555&rnid=283155&sprefix=computer+scien%2Cstripbooks-intl-ship%2C260&ref=sr_st_review-count-rank&ds=v1%3AzLAjg3%2FBIDD%2BKC%2B46%2BR0aF8YM2QVHpBkqLfOfI%2FgK28

    String url = "https://www.amazon.com";

    List<String> urls = new ArrayList<>();
    try {
      WebBrowser browser = new WebBrowser();
      browser.goTo(url);
      while (true) {
        WebPage page = browser.getCurrentWebPage();
        Thread.sleep(5000);
        List<WebElement> elements = page.fetchWebElements(".a-size-mini > .a-link-normal");
        for (WebElement element : elements) {
          String href = element.getAttribute("href");
          urls.add(href);
        }

        System.out.println("Elements on the page: " + elements.size());

        Thread.sleep(5000);

        Optional<WebElement> optionalWebElement = page.fetchWebElement(".s-pagination-next");
        if (optionalWebElement.isEmpty()) {
          break;
        }

        WebElement webElement = optionalWebElement.get();
        webElement.click();
      }

    } catch (Exception e) {
      // ignore
    }

    writeUrlsToFile(urls);
    assertNotNull(urls);
    assertFalse(urls.isEmpty());
  }

  private void writeUrlsToFile(List<String> urls) throws IOException {
    var fileSystem = new DefaultFileSystem();
    File file = fileSystem.createFileIfNotExist(Path.of("C:/Users/Admin/Desktop/urls4.txt"));
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
    urls.forEach(url -> {
      try {
        bufferedWriter.append(url)
          .append("\n");
      } catch (IOException e) {
        // ignore
      }
    });
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testMultiUrlTemplate() {
    MultiUrlTemplateParameters templateParameters = new MultiUrlTemplateParameters.Builder()
      .urlSource(Path.of("C:/Users/Admin/Desktop/urls2.txt"))
      .delayBetweenUrls(5, TimeUnit.SECONDS)
      .build();

    DataNode bookTitle = new DataNode("book", "#productTitle");
    DataNode bookDescription = new DataNode("description", "#bookDescription_feature_div");
    DataNode authorBio = new DataNode("author_bio", "#editorialReviews_feature_div");

    var propertyWrapper = new ListNode("wrapper", "#detailBullets_feature_div .a-list-item");
    var properties =
      new KeyValueNode("#detailBullets_feature_div .a-list-item > span:first-child",
        "#detailBullets_feature_div .a-list-item > span:last-child");
    propertyWrapper.addChild(properties);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(bookTitle);
    tree.addNode(bookDescription);
    tree.addNode(authorBio);
    tree.addNode(propertyWrapper);

    var template = new MultiUrlTemplate(templateParameters, tree);
    var scraper = new MultiUrlTemplateScraper(this::handleFailure);
    DataTable table = scraper.scrape(template);

    exportDataToExcel("books", table);
    assertNotNull(table);
  }

  public void handleFailure(String reasonForFailure, DataTable table) {
    System.out.println("Program crashed because of -> " + reasonForFailure);
    exportDataToExcel("failed_data-" + new Random().nextInt(), table);
  }

  public void exportDataToExcel(String filename, DataTable table) {
    System.out.printf("table has %d rows", table.rows().size());

    Exporter excelExporter = new ExcelExporter();

    DataFile dataFile = new DataFile.Builder()
      .filename(filename)
      .storeAt(Path.of("C:/Users/Admin/Desktop").toString())
      .fileType(FileType.EXCEL)
      .build();

    excelExporter.export(dataFile, table);
  }

  public void exportDataToCsv(String filename, DataTable table) {
    System.out.printf("table has %d rows", table.rows().size());

    // TODO: Don't let user to define both exporter and file type, it might be conflict with each other
    //  user forget to choose csv when he is using CsvExporter.
    Exporter excelExporter = new CsvExporter();

    DataFile dataFile = new DataFile.Builder()
      .filename(filename)
      .storeAt(Path.of("C:/Users/Admin/Desktop").toString())
      .fileType(FileType.CSV)
      .build();

    excelExporter.export(dataFile, table);
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testScrapingAmazonBooksInParallel() throws ExecutionException, InterruptedException {
    MultiUrlTemplateParameters templateParameters = new MultiUrlTemplateParameters.Builder()
      .urlSource(Path.of("C:/Users/Admin/Desktop/urls3.txt"))
      .delayBetweenUrls(5, TimeUnit.SECONDS)
      .build();

    DataNode bookTitle = new DataNode("book", "#productTitle");
    DataNode bookDescription = new DataNode("description", "#bookDescription_feature_div");
    DataNode authorBio = new DataNode("author_bio", "#editorialReviews_feature_div");

    var propertyWrapper = new ListNode("wrapper", "#detailBullets_feature_div .a-list-item");
    var properties =
      new KeyValueNode("#detailBullets_feature_div .a-list-item > span:first-child",
        "#detailBullets_feature_div .a-list-item > span:last-child");
    propertyWrapper.addChild(properties);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(bookTitle);
    tree.addNode(bookDescription);
    tree.addNode(authorBio);
    tree.addNode(propertyWrapper);

    var template = new MultiUrlTemplate(templateParameters, tree);
    List<Task> tasks = template.splitInternally("amazon_book_scraping");

    List<MultiUrlTemplateTaskExecutor> executors = tasks.stream()
      .map((task) -> new MultiUrlTemplateTaskExecutor(task, this::handleFailure))
      .toList();

    ExecutorService executorService = Executors.newFixedThreadPool(executors.size());
    List<Future<DataTable>> futures = executorService.invokeAll(executors);

    DataTable table = new DataTable();
    for (Future<DataTable> future : futures) {
      DataTable dataTable = future.get();
      table.addAll(dataTable.rows());
    }

    exportDataToExcel("books", table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void scrapeComments() throws InterruptedException, ExecutionException {
    MultiUrlTemplateParameters templateParameters = new MultiUrlTemplateParameters.Builder()
      .urlSource(Path.of("C:/Users/Admin/Desktop/urls-for-comments3.txt"))
      .delayBetweenUrls(5, TimeUnit.SECONDS)
      .build();

    //TODO: allow adding multiple selectors for one element so it will try every one of them until it find an element
    // or use regex to define selectors

    // title of comment: .review-title-content > span:last-child
    // rating of comment: .review-rating
    // comment: .review-text-content > span

    var listNode = new ListNode("parent", "div[data-hook=\"review\"]");

    DataNode commentTitle = new DataNode("comment_title", ".review-title-content > span:last-child");
    DataNode commentRating = new DataNode("comment_rating", "span.a-icon-alt");
    DataNode comment = new DataNode("comment", ".review-text-content > span");
    DataNode bookTitle = new DataNode("book", "h1.a-size-large a");

    LinkNode linkNode = new LinkNode("nextPageForComments", ".a-last a");

    listNode.addChild(commentTitle);
    listNode.addChild(commentRating);
    listNode.addChild(comment);
    listNode.addChild(bookTitle);
    listNode.addChild(linkNode);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);

    var template = new MultiUrlTemplate(templateParameters, tree);
    List<Task> tasks = template.splitInternally("amazon_book_comment_scraping");

    List<MultiUrlTemplateTaskExecutor> executors = tasks.stream()
      .map((task) -> new MultiUrlTemplateTaskExecutor(task, this::handleFailure))
      .toList();

    ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());
    List<Future<DataTable>> futures = executorService.invokeAll(executors);

    DataTable table = new DataTable();
    for (Future<DataTable> future : futures) {
      DataTable dataTable = future.get();
      table.addAll(dataTable.rows());
    }

    exportDataToCsv("comments", table);
    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

  static class MultiUrlTemplateTaskExecutor implements Callable<DataTable> {
    private final Task task;
    private final ScrapeErrorCallback callback;

    MultiUrlTemplateTaskExecutor(Task task, ScrapeErrorCallback callback) {
      this.task = task;
      this.callback = callback;
    }

    @Override
    public DataTable call() {
      MultiUrlTemplate template = (MultiUrlTemplate) task.getTemplate();
      var scraper = new MultiUrlTemplateScraper(callback);
      DataTable table = scraper.scrape(template);
      System.out.printf("""
        Task %s, completed its job and it collects %d rows.\s
        """, task.getId(), table.rows().size());
      return table;
    }
  }

  @Test
  @Tag(TestConstants.LONG_LASTING_TEST)
  void testAgency() {
    var listNode = new ListNode("agent", ".c-agent-item");
    var actionNode = new ActionNode("phone_link", ".o-phone", Action.CLICK);
    listNode.addChild(actionNode);

    var secondPartListNode = new ListNode("agent", ".c-agent-item");
    var agentName = new DataNode("agent_name", ".agent-item__name");
    var timeoutNode = new TimeoutNode(3, TimeUnit.SECONDS);
    var phoneNumber = new DataNode("phone_number", ".o-phone");

    secondPartListNode.addChild(agentName);
    secondPartListNode.addChild(timeoutNode);
    secondPartListNode.addChild(phoneNumber);

    DataTree<Node> tree = new DataTree<>();
    tree.addNode(listNode);
    tree.addNode(secondPartListNode);

    var pageParameters = new PageParameters.Builder()
      .url("https://www.christiesrealestate.com/associates/int/%s-pg".formatted(PAGE_SPECIFIER))
      .pageRange(1, 50)
      .delayBetweenPages(6000)
      .build();

    var paginationTemplate = new PaginationTemplate(pageParameters, tree);

    Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
    DataTable table = scraper.scrape(paginationTemplate);

    ExcelExporter excelExporter = new ExcelExporter();

    DataFile dataFile = new DataFile.Builder()
      .filename("agencies")
      .fileType(FileType.EXCEL)
      .storeAt(Path.of("C:/Users/Admin/Desktop").toString())
      .build();

    excelExporter.export(dataFile, table);

    assertNotNull(table);
    assertFalse(table.rows().isEmpty());
  }

}
