package az.my.datareport.controller;

import az.my.datareport.service.ConfigService;
import az.my.datareport.service.ExportService;
import az.my.datareport.service.ScraperService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@ExtendWith(MockitoExtension.class)
class ConfigControllerTest {

    private static String json;
    private MockMvc mvc;
    @InjectMocks
    private ConfigController configController;
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
        mvc = MockMvcBuilders.standaloneSetup(configController)
                .build();
    }

    @Test
    void testPostData_whenJsonDataSend_redirectToResultPage() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/config/send").content(json))
                .andExpect(redirectedUrl("/result"))
                .andReturn();

        assertNotNull(mvcResult.getModelAndView());
        assertEquals("redirect:/result", mvcResult.getModelAndView().getViewName());
    }

}