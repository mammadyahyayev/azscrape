package az.my.datareport;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.scrape.templates.pagination.PageParameters;
import az.my.datareport.scrape.templates.pagination.Pagination;
import az.my.datareport.scrape.templates.pagination.PaginationPageScraper;
import az.my.datareport.scrape.templates.scroll.ScrollablePage;
import az.my.datareport.scrape.templates.scroll.ScrollablePageParameters;
import az.my.datareport.scrape.templates.scroll.ScrollablePageScraper;
import az.my.datareport.tree.DataNode;
import az.my.datareport.tree.DataTree;
import az.my.datareport.tree.ReportDataTable;
import org.junit.jupiter.api.Test;

import static az.my.datareport.scrape.templates.pagination.PageParameters.PAGE_SPECIFIER;

class DataReportApplicationTest {
    @Test
    void testExporting() {
        var pageParameters = new PageParameters.Builder()
                .url("https://bina.az/baki/alqi-satqi/menziller/yeni-tikili/1-otaqli?page=" + PAGE_SPECIFIER)
                .pageRange(1, 34)
                .delayBetweenPages(6000)
                .build();


        var repoItem = new DataTree<>(new DataNode("repoItem", ".items-i"));
        var location = new DataTree<>(new DataNode("location", ".card_params .location"));
        var price = new DataTree<>(new DataNode("price", ".card_params .price-val"));
        var currency = new DataTree<>(new DataNode("currency", ".card_params .price-cur"));
        var roomCount = new DataTree<>(new DataNode("room count", ".card_params .name > li:nth-child(1)"));
        var area = new DataTree<>(new DataNode("area", ".card_params .name > li:nth-child(2)"));
        var floor = new DataTree<>(new DataNode("floor", ".card_params .name > li:nth-child(3)"));

        repoItem.addSubNode(location);
        repoItem.addSubNode(price);
        repoItem.addSubNode(currency);
        repoItem.addSubNode(roomCount);
        repoItem.addSubNode(area);
        repoItem.addSubNode(floor);

        Pagination tree = new Pagination(pageParameters, repoItem);

        Scraper<Pagination> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        ExcelExporter excelExporter = new ExcelExporter();

        ReportFile reportFile = new ReportFile.Builder()
                .filename("one_room_apartment")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(reportFile, table);
    }

    @Test
    void testContactHome() {
        var pageParameters = new PageParameters.Builder()
                .url("https://kontakt.az/telefonlar/mobil-telefonlar/page/" + PAGE_SPECIFIER)
                .pageRange(1, 5)
                .delayBetweenPages(3000)
                .build();


        var repoItem = new DataTree<>(new DataNode("repoItem", ".cart-item"));
        var phone = new DataTree<>(new DataNode("name", ".cart-body-top .name > a", true));
        var price = new DataTree<>(new DataNode("price", ".cart-footer > p .nprice"));
        var currency = new DataTree<>(new DataNode("currency", ".cart-footer > p .nprice + small"));

        repoItem.addSubNode(phone);
        repoItem.addSubNode(price);
        repoItem.addSubNode(currency);

        Pagination tree = new Pagination(pageParameters, repoItem);

        Scraper<Pagination> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        ExcelExporter excelExporter = new ExcelExporter();

        ReportFile reportFile = new ReportFile.Builder()
                .filename("smartphones")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(reportFile, table);
    }

    @Test
    void testScrollablePageTurboAz() {
        var pageParameters = new ScrollablePageParameters.Builder()
                .url("https://turbo.az/")
                .build();

        var repoItem = new DataTree<>(new DataNode("wrapper", ".products-i"));
        var car = new DataTree<>(new DataNode("car", ".products-i__name", true));
        var price = new DataTree<>(new DataNode("price", ".products-i__price .product-price"));
        var details = new DataTree<>(new DataNode("details", ".products-i__attributes"));

        repoItem.addSubNode(car);
        repoItem.addSubNode(price);
        repoItem.addSubNode(details);

        var tree = new ScrollablePage(pageParameters, repoItem);

        Scraper<ScrollablePage> scraper = new ScrollablePageScraper();
        ReportDataTable table = scraper.scrape(tree);

        var excelExporter = new ExcelExporter();

        var reportFile = new ReportFile.Builder()
                .filename("turbo_az")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(reportFile, table);

    }
}