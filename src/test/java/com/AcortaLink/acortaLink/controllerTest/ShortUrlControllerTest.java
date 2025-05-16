package com.AcortaLink.acortaLink.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.awt.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.startsWith;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.profiles.active=dev")
class ShortUrlControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void postShortUrl() throws Exception{
        String json = "{\"url\":\"https://www.youtube.com/\"}";

        mockMvc.perform(post("/shorten").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").isNotEmpty())
                .andExpect(jsonPath("$.clickCount").value(0))
                .andExpect(jsonPath("$.urlShort").value(startsWith("http://localhost/")));
    }


    @Test
    void getNonExist() throws Exception {
        mockMvc.perform(get("/Mkfi3o"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado"));
    }
}
