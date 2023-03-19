package com.kakao.blog.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.blog.dto.BlogResDto;
import com.kakao.blog.infrastructure.httpclient.kakao.KakaoApiClient;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoBlog;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoReqDto;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoResDto;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.Meta;
import com.kakao.blog.infrastructure.httpclient.naver.NaverApiClient;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverBlog;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverReqDto;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverResDto;
import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.RequestTemplate;
import feign.Response;
import feign.RetryableException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BlogSearchServiceTest {

    @MockBean
    KakaoApiClient kakaoApiClient;

    @MockBean
    NaverApiClient naverApiClient;

    @Autowired
    BlogSearchService blogSearchService;

    @Test
    void getBlogListByCondition_KakaoAPiIsOk() {
        //give
        given(kakaoApiClient.searchBlog(any(KakaoReqDto.class)))
          .willReturn(KakaoResDto.<KakaoBlog>builder()
            .meta(Meta.builder().isEnd(true).totalCount(0).build())
            .documents(Collections.emptyList())
            .build());

        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<BlogResDto> result = blogSearchService.getBlogListByCondition("test", "accuracy", pageRequest);

        //then
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);
        assertThat(result.getNumberOfElements()).isEqualTo(0);
        assertThat(result.getContent()).hasSize(0);

    }
    @Test
    void getBlogListByCondition_KakaoAPiThrowsException() {
        //give
        given(kakaoApiClient.searchBlog(any(KakaoReqDto.class)))
          .willThrow(new RetryableException(500, "serverError", HttpMethod.GET, null, this.mockRequest("https://dapi.kakao.com")));
        given(naverApiClient.searchBlog(any(NaverReqDto.class)))
          .willReturn(NaverResDto.<NaverBlog>builder()
            .lastBuildDate(LocalDateTime.now())
            .total(100)
            .start(1)
            .display(1)
            .items(List.of(NaverBlog.builder().build())).build());

        PageRequest pageRequest = PageRequest.of(0, 1);

        //when
        Page<BlogResDto> result = blogSearchService.getBlogListByCondition("test", "accuracy", pageRequest);

        //then
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getTotalPages()).isEqualTo(100);
        assertThat(result.getNumberOfElements()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);

    }

    private Request mockRequest(String url) {
        return Request.create(HttpMethod.GET, url, Collections.emptyMap(), Body.create("test".getBytes()),
          new RequestTemplate());
    }
}