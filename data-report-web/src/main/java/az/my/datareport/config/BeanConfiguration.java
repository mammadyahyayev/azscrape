package az.my.datareport.config;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.scrape.WebScraper2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public WebScraper2 scraper() {
        return new WebScraper2();
    }

    @Bean
    public ExcelExporter excelExporter() {
        return new ExcelExporter();
    }

}
