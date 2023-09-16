package az.caspian.app;

import az.caspian.core.constant.TestConstants;
import az.caspian.core.model.DataFile;
import az.caspian.core.model.enumeration.FileExtension;
import az.caspian.core.model.enumeration.FileType;
import az.caspian.core.tree.*;
import az.caspian.export.ExcelExporter;
import az.caspian.scrape.templates.Scraper;
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

import java.io.File;
import java.nio.file.Path;

import static az.caspian.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AzScrapeApplicationTest {
    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testExporting() {
        var pageParameters = new PageParameters.Builder()
                .url("https://bina.az/baki/alqi-satqi/menziller/yeni-tikili/1-otaqli?page=" + PAGE_SPECIFIER)
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

        DataTree<Node> tree = new DataTree<>(listNode);
        tree.addNode(location);
        tree.addNode(price);
        tree.addNode(currency);
        tree.addNode(roomCount);
        tree.addNode(area);
        tree.addNode(floor);

        PaginationTemplate paginationTemplate = new PaginationTemplate(pageParameters, tree);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(paginationTemplate);

        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("one_room_apartment")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .storeAt(Path.of("C:/Users/User/Desktop").toString())
                .build();

        excelExporter.export(dataFile, table);
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

        DataTree<Node> tree = new DataTree<>(listNode);
        tree.addNode(phone);
        tree.addNode(price);
        tree.addNode(currency);

        PaginationTemplate paginationTemplate = new PaginationTemplate(pageParameters, tree);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(paginationTemplate);

        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("smartphones")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
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

        DataTree<Node> tree = new DataTree<>(listNode);
        tree.addNode(car);
        tree.addNode(price);
        tree.addNode(details);

        var template = new ScrollablePageTemplate(pageParameters, tree);

        Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
        ReportDataTable table = scraper.scrape(template);

        var excelExporter = new ExcelExporter();

        var reportFile = new DataFile.Builder()
                .filename("turbo_az")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(reportFile, table);
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

        DataTree<Node> tree = new DataTree<>(listNode);
        tree.addNode(car);
        tree.addNode(price);
        tree.addNode(details);

        PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(template);

        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("turbo_az")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(dataFile, table);
    }

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testCallbackCalledWhenInternetConnectionGone() {
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .pageRange(1, 416)
                .delayBetweenPages(3000)
                .build();


        var listNode = new ListNode("listNode", ".products-i");
        var car = new DataNode("car", ".products-i__name");
        var price = new DataNode("price", ".products-i__price .product-price");
        var details = new DataNode("details", ".products-i__attributes");

        DataTree<Node> tree = new DataTree<>(listNode);
        tree.addNode(car);
        tree.addNode(price);
        tree.addNode(details);

        PaginationTemplate template = new PaginationTemplate(pageParameters, tree);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper(this::callback);
        assertThrows(Exception.class, () -> scraper.scrape(template));
    }

    void callback(String message, ReportDataTable data) {
        System.out.println(message);
        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("turbo_az_with_callback")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(dataFile, data);
    }

    @Test
    @Tag(TestConstants.LONG_LASTING_TEST)
    void testPaginationItemVisitorTemplate() {
        var pageParameters = new PageParameters.Builder()
                .url("https://turbo.az/autos?page=" + PAGE_SPECIFIER)
                .pageNum(1)
                .delayBetweenPages(3000)
                .build();

        var link = new LinkNode("link", ".products-i__link");
        var carNode = new DataNode("car", ".product-title");
        var price = new DataNode("price", ".product-price > div:first-child");
        var advertisementId = new DataNode("advertisement number", ".product-actions__id");
        var description = new DataNode("description", ".product-description__content");
        var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
        var viewCount = new DataNode("view count", ".product-statistics__i:last-child");
        var properties = new KeyValueDataNode(".product-properties__i",
                ".product-properties__i-name",
                ".product-properties__i-value"
        );

        DataTree<Node> tree = new DataTree<>(link);
        tree.addNode(carNode);
        tree.addNode(price);
        tree.addNode(advertisementId);
        tree.addNode(description);
        tree.addNode(updateTime);
        tree.addNode(viewCount);
        tree.addNode(properties);

        PaginationItemVisitorTemplate template = new PaginationItemVisitorTemplate(pageParameters, tree);

        PaginationItemVisitorScraper scraper = new PaginationItemVisitorScraper();
        ReportDataTable table = scraper.scrape(template);

        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("turbo_az")
                .storeAt(Path.of("C:/Users/User/Desktop").toString())
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(dataFile, table);
    }

    // TODO: 2509 - bina.az https://bina.az/alqi-satqi?page=2509
}