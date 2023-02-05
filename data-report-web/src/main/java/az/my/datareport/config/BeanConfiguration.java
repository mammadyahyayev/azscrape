package az.my.datareport.config;

import az.my.datareport.exporter.ExcelExporter;
import az.my.datareport.scrape.WebScraper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public WebScraper scraper() {
        return new WebScraper();
    }

    @Bean
    public ExcelExporter excelExporter() {
        return new ExcelExporter();
    }

    @Bean
    public ConfigLoader configLoader() {
        return new ConfigLoader();
    }
}
