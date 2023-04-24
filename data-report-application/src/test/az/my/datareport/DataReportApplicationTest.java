package az.my.datareport;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.scrape.PaginationPageScraper;
import az.my.datareport.scrape.Scraper;
import az.my.datareport.tree.*;
import org.junit.jupiter.api.Test;


class DataReportApplicationTest {

    @Test
    void testExporting() {
        var pageParameters = new PageParameters.Builder()
                .url("https://bina.az/baki/alqi-satqi/menziller/yeni-tikili/1-otaqli?page={pageNum}")
                .pageRange(1, 30)
                .delayBetweenPages(6000)
                .build();


        DataTree<DataNode> repoItem = new DataTree<>(new DataNode("repoItem", ".items-i"));
        DataTree<DataNode> location = new DataTree<>(new DataNode("location", ".card_params .location"));
        DataTree<DataNode> price = new DataTree<>(new DataNode("price", ".card_params .price-val"));
        DataTree<DataNode> currency = new DataTree<>(new DataNode("currency", ".card_params .price-cur"));
//        DataTree<DataNode> details = new DataTree<>(new DataNode("details", ".card_params .name"));

        repoItem.addSubNode(location);
        repoItem.addSubNode(price);
        repoItem.addSubNode(currency);
//        repoItem.addSubNode(details);

        Pagination tree = new Pagination(pageParameters, repoItem);

        Scraper<Pagination> scraper = new PaginationPageScraper();
        ReportDataTable table = scraper.scrape(tree);

        ExcelExporter excelExporter = new ExcelExporter();

        ReportFile reportFile = new ReportFile.Builder()
                .filename("1 otaqli ev binaaz")
                .fileType(FileType.EXCEL)
                .fileExtension(FileExtension.XLSX)
                .build();

        excelExporter.export(reportFile, table);
    }
}