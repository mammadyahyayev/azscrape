package az.my.datareport.config;

import az.my.datareport.exporter.ExcelExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public ExcelExporter excelExporter() {
        return new ExcelExporter();
    }

}
