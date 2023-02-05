package az.my.datareport;

import az.my.datareport.scrape.WebScraper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataReportWebApplication {
    @Bean
    public WebScraper scraper() {
        return new WebScraper();
    }

    public static void main(String[] args) {
        SpringApplication.run(DataReportWebApplication.class);
    }

}
