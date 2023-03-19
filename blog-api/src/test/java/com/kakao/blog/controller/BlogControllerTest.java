package com.kakao.blog.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakao.blog.dto.BlogResDto;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoBlog;
import com.kakao.blog.service.BlogService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BlogControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BlogService blogService;

    @Test
    void searchBlog() throws Exception{
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(blogService.getBlogListByCondition("test", "accuracy", pageRequest))
          .willReturn(new PageImpl<>(List.of(new BlogResDto(KakaoBlog.builder().dateTime(LocalDateTime.now()).build())), pageRequest, 1));

        // When
        ResultActions resultActions = mockMvc.perform(get("/api/v1/blog/search")
            .param("keyword", "test")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.totalPages").value(1));
        resultActions.andExpect(jsonPath("$.first").value("true"));
        resultActions.andExpect(jsonPath("$.currentPageNumber").value(0));
        resultActions.andExpect(jsonPath("$.hasNext").value("false"));
        resultActions.andExpect(jsonPath("$.dataList").exists());

    }
}