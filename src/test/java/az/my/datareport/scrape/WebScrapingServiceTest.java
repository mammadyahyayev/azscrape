package az.my.datareport.scrape;

import az.my.datareport.ast.DataAST;
import az.my.datareport.ast.DataElement;
import az.my.datareport.model.ReportData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WebScrapingServiceTest {

    private final String url = "https://github.com/search?q=java";
    private DataAST dataAST;

    @BeforeEach
    public void setUp() {
        DataElement dataElement = new DataElement("title", ".repo-list-item  .v-align-middle");
        DataElement dataElement2 = new DataElement("description", ".repo-list-item  .mb-1");

        dataAST = new DataAST();
        dataAST.addChildDataElement(dataElement);
        dataAST.addChildDataElement(dataElement2);
    }

    @Test
    void collected_data_size_should_be_greater_than_zero() {
        WebScrapingService service = new WebScrapingService();
        List<ReportData> data = service.scrape(url, dataAST);
        data.forEach(d -> assertTrue(d.values().size() > 0));
    }

}