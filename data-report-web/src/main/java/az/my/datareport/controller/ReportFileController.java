package az.my.datareport.controller;

import az.my.datareport.DataReportAppException;
import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.ReportData;
import az.my.datareport.model.ReportFile;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import az.my.datareport.tree.DataAST;
import org.apache.hc.core5.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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
    public ModelAndView generateReportFile(@RequestBody String json, HttpServletResponse response) {
        try {
            DataAST dataAST = configService.sendConfigStr(json);
            ReportFile reportFile = configService.getReportFileConfiguration();
            ReportData reportData = scraperService.getScrapedData(dataAST);
            boolean isExported = exportService.export(reportFile, reportData);

            response.setContentType(String.valueOf(ContentType.TEXT_HTML));
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
    public ResponseEntity<Resource> downloadReportFile(HttpServletResponse response) {
        ReportFile reportFile = configService.getReportFileConfiguration();
        Path reportFilePath = Path.of(FileConstants.TEMP_DIR_PATH, reportFile.getFileFullName());

        try {
            InputStream inputStream = new FileInputStream(String.valueOf(reportFilePath.toAbsolutePath()));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportFile.getFileFullName());
            response.setContentType(ContentType.APPLICATION_OCTET_STREAM.toString());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inputStreamResource);
        } catch (IOException e) {
            throw new DataReportAppException("File related exception happened!", e);
        }
    }

}
