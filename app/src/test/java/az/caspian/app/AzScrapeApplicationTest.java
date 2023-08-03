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
import az.caspian.scrape.templates.pagination.item.PaginationItemPageScraper;
import az.caspian.scrape.templates.pagination.item.PaginationItemTemplate;
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
                .pageRange(1, 34)
                .delayBetweenPages(6000)
                .build();


        var repoItem = new DataTree<>(new Node("repoItem", ".items-i"));
        var location = new DataTree<>(new Node("location", ".card_params .location"));
        var price = new DataTree<>(new Node("price", ".card_params .price-val"));
        var currency = new DataTree<>(new Node("currency", ".card_params .price-cur"));
        var roomCount = new DataTree<>(new Node("room count", ".card_params .name > li:nth-child(1)"));
        var area = new DataTree<>(new Node("area", ".card_params .name > li:nth-child(2)"));
        var floor = new DataTree<>(new Node("floor", ".card_params .name > li:nth-child(3)"));

        repoItem.addSubNode(location);
        repoItem.addSubNode(price);
        repoItem.addSubNode(currency);
        repoItem.addSubNode(roomCount);
        repoItem.addSubNode(area);
        repoItem.addSubNode(floor);

        PaginationTemplate tree = new PaginationTemplate(pageParameters, repoItem);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        ExcelExporter excelExporter = new ExcelExporter();

        DataFile dataFile = new DataFile.Builder()
                .filename("one_room_apartment")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
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


        var repoItem = new DataTree<>(new Node("repoItem", ".cart-item"));
        var phone = new DataTree<>(new Node("name", ".cart-body-top .name > a", true));
        var price = new DataTree<>(new Node("price", ".cart-footer > p .nprice"));
        var currency = new DataTree<>(new Node("currency", ".cart-footer > p .nprice + small"));

        repoItem.addSubNode(phone);
        repoItem.addSubNode(price);
        repoItem.addSubNode(currency);

        PaginationTemplate tree = new PaginationTemplate(pageParameters, repoItem);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

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

        var repoItem = new DataTree<>(new Node("wrapper", ".products-i"));
        var car = new DataTree<>(new Node("car", ".products-i__name", true));
        var price = new DataTree<>(new Node("price", ".products-i__price .product-price"));
        var details = new DataTree<>(new Node("details", ".products-i__attributes"));

        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(details);

        var tree = new ScrollablePageTemplate(pageParameters, repoItem);

        Scraper<ScrollablePageTemplate> scraper = new ScrollablePageScraper();
        ReportDataTable table = scraper.scrape(tree);

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


        var repoItem = new DataTree<>(new Node("wrapper", ".products-i"));
        var car = new DataTree<>(new Node("car", ".products-i__name", true));
        var price = new DataTree<>(new Node("price", ".products-i__price .product-price"));
        var details = new DataTree<>(new Node("details", ".products-i__attributes"));

        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(details);

        PaginationTemplate tree = new PaginationTemplate(pageParameters, repoItem);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

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


        var repoItem = new DataTree<>(new Node("wrapper", ".products-i"));
        var car = new DataTree<>(new Node("car", ".products-i__name", true));
        var price = new DataTree<>(new Node("price", ".products-i__price .product-price"));
        var details = new DataTree<>(new Node("details", ".products-i__attributes"));

        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(details);

        PaginationTemplate tree = new PaginationTemplate(pageParameters, repoItem);

        Scraper<PaginationTemplate> scraper = new PaginationPageScraper(this::callback);
        assertThrows(Exception.class, () -> scraper.scrape(tree));
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

        var link = new DataNode("link", ".products-i__link");
        link.setLink(true);

        DataTree<Node> tree = new DataTree<>(link);

        var carNode = new DataNode("car", ".product-title", true);
        var price = new DataNode("price", ".product-price > div:first-child");
        var advertisementId = new DataNode("advertisement number", ".product-actions__id");
        var description = new DataNode("description", ".product-description__content");
        var updateTime = new DataNode("update time", ".product-statistics__i:first-child");
        var viewCount = new DataNode("view count", ".product-statistics__i:last-child");
        var properties = new KeyValueDataNode(".product-properties__i",
                ".product-properties__i-name",
                ".product-properties__i-value"
        );

        tree.addChild(carNode, link);
        tree.addChild(price, link);
        tree.addChild(advertisementId, link);
        tree.addChild(description, link);
        tree.addChild(updateTime, link);
        tree.addChild(viewCount, link);
        tree.addChild(properties, link);

        PaginationItemTemplate template = new PaginationItemTemplate(pageParameters, tree);

        PaginationItemPageScraper scraper = new PaginationItemPageScraper(this::callback);
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