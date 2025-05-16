package com.AcortaLink.acortaLink.controllerTest;

import com.AcortaLink.acortaLink.Controllers.ShortUrlController;
import com.AcortaLink.acortaLink.Services.ShortUrlService;
import com.AcortaLink.acortaLink.exceptions.CodeNotFoundException;
import com.AcortaLink.acortaLink.exceptions.RestExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.startsWith;

@WebMvcTest(ShortUrlController.class)
@Import(RestExceptionHandler.class)
class ShortUrlControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShortUrlService service;

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

        when(service.getAndIncrement("Mkfi3o")).thenThrow(new CodeNotFoundException("Mkfi3o"));

        mockMvc.perform(get("/Mkfi3o"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado"));
    }
}
