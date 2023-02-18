package az.my.datareport.controller;

import az.my.datareport.constant.FileConstants;
import az.my.datareport.model.ReportFile;
import az.my.datareport.model.enumeration.FileExtension;
import az.my.datareport.model.enumeration.FileType;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import az.my.datareport.utils.FileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
class ReportFileControllerTest {

    private static String json;

    private MockMvc mvc;

    @InjectMocks
    private ReportFileController reportFileController;
    @Mock
    private ConfigService configService;
    @Mock
    private ScraperService scraperService;
    @Mock
    private ExportService exportService;

    @BeforeAll
    public static void setupAll() {
        json = "{\n" +
                "  \"exported_file_name\": \"Github Search\",\n" +
                "  \"exported_file_type\": \"EXCEL\",\n" +
                "  \"exported_file_type_extension\": \"xlsx\",\n" +
                "  \"description\": \"Dummy Description\",\n" +
                "  \"data\": {\n" +
                "    \"url\": \"https://github.com/search?q=java\",\n" +
                "    \"element\": {\n" +
                "      \"selector\": \".repo-list-item\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"title\",\n" +
                "          \"selector\": \".v-align-middle\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"description\",\n" +
                "          \"selector\": \".mb-1\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(reportFileController)
                .build();
    }

    @Test
    void testPostData_whenFileNotExported_redirectToErrorPage() throws Exception {
        when(exportService.export(Mockito.any(), Mockito.any())).thenReturn(false);

        MvcResult mvcResult = mvc.perform(post("/reportFile/generate").content(json))
                .andReturn();

        assertNotNull(mvcResult.getModelAndView());
        assertEquals("redirect:/error", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void testPostData_whenFileExported_redirectToResultPage() throws Exception {
        when(exportService.export(Mockito.any(), Mockito.any())).thenReturn(true);

        MvcResult mvcResult = mvc.perform(post("/reportFile/generate").content(json))
                .andReturn();

        assertNotNull(mvcResult.getModelAndView());
        assertEquals("redirect:/result", mvcResult.getModelAndView().getViewName());
    }

    @Test
    void testDownloadFile_whenFilePathGiven_returnExportedFile() throws Exception {
        Path path = Path.of(FileConstants.TEMP_DIR_PATH, "file.xlsx");

        FileManager fileManager = new FileManager();
        fileManager.constructFile(path.toString());

        ReportFile reportFile = new ReportFile.Builder()
                .filename("file")
                .fileExtension(FileExtension.XLSX)
                .fileType(FileType.EXCEL)
                .build();

        when(configService.getReportFileConfiguration()).thenReturn(reportFile);

        mvc.perform(get("/reportFile/download"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/octet-stream"))
                .andReturn();

        assertTrue(fileManager.deleteFile(path));
    }


}