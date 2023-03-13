package az.my.datareport.controller;

import az.my.datareport.DataReportWebApplication;
import az.my.datareport.constant.FileConstants;
import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = DataReportWebApplication.class)
public class ReportFileControllerIntTest {

    private static String json;

    @Autowired
    public ConfigService configService;

    private MockMvc mvc;

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private ExportService exportService;

    @BeforeAll
    public static void setupAll() {
        json = "{\n" +
                "  \"file_name\": \"Github Search\",\n" +
                "  \"file_type\": \"EXCEL\",\n" +
                "  \"file_extension\": \"xlsx\",\n" +
                "  \"description\": \"Dummy Description\",\n" +
                "  \"data_parts\": [\n" +
                "    {\n" +
                "      \"url\": \"https://github.com/search?q=java\",\n" +
                "      \"elements\": [\n" +
                "        {\n" +
                "          \"name\": \"repository\",\n" +
                "          \"selector\": \".repo-list-item\",\n" +
                "          \"sub_elements\": [\n" +
                "            {\n" +
                "              \"name\": \"title\",\n" +
                "              \"selector\": \".v-align-middle\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"description\",\n" +
                "              \"selector\": \".mb-1\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    @BeforeEach
    public void setup() {
        ReportFileController reportFileController =
                new ReportFileController(configService, scraperService, exportService);

        this.mvc = MockMvcBuilders.standaloneSetup(reportFileController)
                .build();
    }

    @Test
    public void testGenerateReportFile_whenJSONGiven_createAndRedirectToResultPage() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/reportFile/generate").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(content().contentType(ContentType.TEXT_HTML.toString()))
                .andReturn();

        File file = new File(FileConstants.TEMP_DIR_PATH, "github_search.xlsx");

        assertNotNull(mvcResult.getModelAndView());
        assertEquals("redirect:/result", mvcResult.getModelAndView().getViewName());
        assertTrue(file.exists());
    }

}
