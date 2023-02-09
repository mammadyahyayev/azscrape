package az.my.datareport.controller;

import az.my.datareport.config.ConfigurationException;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import az.my.datareport.tree.DataAST;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;

@Controller
public class ConfigController {

    public final ConfigService configService;
    private final ScraperService scraperService;
    private final ExportService exportService;

    private ReportFile reportFile;

    public ConfigController(
            ConfigService configService, ScraperService scraperService, ExportService exportService
    ) {
        this.configService = configService;
        this.scraperService = scraperService;
        this.exportService = exportService;
    }

    @PostMapping("/config/send") //TODO: Change url both here and javascript
    public String postData(@RequestBody String json) {
        try {
            DataAST dataAST = configService.sendConfigStr(json);
            reportFile = configService.getFileConfiguration();
            ReportData reportData = scraperService.getScrapedData(dataAST);
            exportService.export(reportFile, reportData);
        } catch (ConfigurationException ex) {
            /*return new ResponseEntity<>(
                    "Exception: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR
            );*/
            //TODO: log and show message to end user.
            //TODO: Add logging dependency to distribution management
        }
        //TODO: create a connector class between controller and service
        // send json string to end connection, it must throw an exception if there is a problem
        // catch that exception in here and show the problem to user

        return "redirect:result";
    }

    @GetMapping("/download/file")
    public ResponseEntity<Resource> downloadReportFile() {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("file.xlsx");

        if (inputStream == null) {
            throw new RuntimeException("File not found");
        }

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
