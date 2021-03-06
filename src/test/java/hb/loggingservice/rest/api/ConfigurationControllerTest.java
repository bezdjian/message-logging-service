package hb.loggingservice.rest.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConfigurationControllerTest {

    private static final String BASE_URL = "/configure";
    private static final String CONFIG_PARAM_VALUE = "MAX_AGE";
    private static final String CONFIG_PARAM = "configurationName";
    private static final String SUCCESSFULLY_CREATED_MSG = "Configuration has been successfully saved for %s";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should save a configuration with given name and value")
    void shouldConfigureMaxAge() throws Exception {
        //Given
        int value = 10;
        //When & expect
        String expectedResponseMessage = String.format(SUCCESSFULLY_CREATED_MSG, CONFIG_PARAM_VALUE);
        mockMvc.perform(put(BASE_URL + "/" + value)
            .queryParam(CONFIG_PARAM, CONFIG_PARAM_VALUE))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.message").value(expectedResponseMessage));
    }

    @Test
    @DisplayName("Should throw bad request when configuration name is missing")
    void shouldThrowBadRequestWhenConfigurationNameIsMissing() throws Exception {
        //Given
        int value = 10;
        //When & expect
        mockMvc.perform(put(BASE_URL + "/" + value))
            .andExpect(status().is4xxClientError());
    }
}