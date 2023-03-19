package com.kakao.blog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kakao.blog.dto.BlogResDto;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BlogServiceTest {

    @MockBean
    QueueService queueService;

    @MockBean
    BlogSearchService blogSearchService;

    @Autowired
    BlogService blogService;

    @Test
    void getBlogListByCondition() {
        //given
        willDoNothing().given(queueService).sendEvent("test");
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(blogSearchService.getBlogListByCondition("test", "recency", pageRequest))
          .willReturn(new PageImpl<>(Collections.emptyList(), pageRequest, 0));

        //when
        Page<BlogResDto> result = blogService.getBlogListByCondition("test", "recency", pageRequest);

        //then
        assertThat(result.getTotalElements()).isZero();
        assertThat(result.getTotalPages()).isZero();
        assertThat(result.getContent()).isEmpty();
        verify(queueService, times(1)).sendEvent("test");

    }
}