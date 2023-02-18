package az.my.datareport.controller;

import az.my.datareport.DataReportAppException;
import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import az.my.datareport.tree.DataAST;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Controller
@RequestMapping("/reportFile")
public class ReportFileController {

    private static final Logger LOG = LogManager.getLogger(ReportFileController.class);

    public final ConfigService configService;
    private final ScraperService scraperService;
    private final ExportService exportService;

    public ReportFileController(
            ConfigService configService, ScraperService scraperService, ExportService exportService
    ) {
        this.configService = configService;
        this.scraperService = scraperService;
        this.exportService = exportService;
    }

    @PostMapping(value = "/generate", produces = "text/html")
    @ResponseBody
    public ModelAndView postData(@RequestBody String json) {
        try {
            DataAST dataAST = configService.sendConfigStr(json);
            ReportFile reportFile = configService.getReportFileConfiguration();
            ReportData reportData = scraperService.getScrapedData(dataAST);
            boolean isExported = exportService.export(reportFile, reportData);
            if (!isExported) {
                LOG.warn("File wasn't generated!");
                return new ModelAndView("redirect:/error");
            }
        } catch (DataReportAppException ex) {
            LOG.error("Failed to create file", ex);
            return new ModelAndView("redirect:/error");
        }

        return new ModelAndView("redirect:/result");
    }

    @GetMapping(value = "/download", produces = "application/octet-stream")
    public ResponseEntity<Resource> downloadReportFile() {
        ReportFile reportFile = configService.getReportFileConfiguration();
        Path reportFilePath = Path.of(FileConstants.TEMP_DIR_PATH, reportFile.getFileFullName());

        try {
            InputStream inputStream = new FileInputStream(String.valueOf(reportFilePath.toAbsolutePath()));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);
        } catch (IOException e) {
            throw new DataReportAppException("File related exception happened!", e);
        }
    }

}
