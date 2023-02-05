package az.my.datareport.controller;

import az.my.datareport.config.ConfigurationException;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import az.my.datareport.tree.DataAST;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ConfigController {

    public final ConfigService configService;
    private final ScraperService scraperService;
    private final ExportService exportService;

    public ConfigController(
            ConfigService configService, ScraperService scraperService, ExportService exportService
    ) {
        this.configService = configService;
        this.scraperService = scraperService;
        this.exportService = exportService;
    }

    @PostMapping("/config/send") //TODO: Change url both here and javascript
    public ResponseEntity<String> postData(@RequestBody String json) {
        try {
            DataAST dataAST = configService.sendConfigStr(json);
            ReportFile reportFile = configService.getFileConfiguration();
            ReportData reportData = scraperService.getScrapedData(dataAST);
            exportService.export(reportFile, reportData);
        } catch (ConfigurationException ex) {
            return new ResponseEntity<>(
                    "Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );
            //TODO: log and show message to end user.
            //TODO: Add logging dependency to distribution management
        }
        //TODO: create a connector class between controller and service
        // send json string to end connection, it must throw an exception if there is a problem
        // catch that exception in here and show the problem to user

        return new ResponseEntity<>("Configurations received...", HttpStatus.OK);
    }

}
