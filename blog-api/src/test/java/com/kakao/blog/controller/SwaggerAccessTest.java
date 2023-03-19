package com.kakao.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SwaggerAccessTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void access_swagger() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc.perform(get("/swagger-ui/index.html")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void accessDenied() throws Exception {
        // Given
        // When
        ResultActions resultActions = mockMvc.perform(get("/test")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isForbidden());
    }
}
