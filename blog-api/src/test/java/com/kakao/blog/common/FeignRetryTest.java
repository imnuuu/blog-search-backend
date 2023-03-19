package com.kakao.blog.common;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kakao.blog.infrastructure.httpclient.kakao.KakaoApiClient;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoReqDto;
import feign.Client;
import feign.Feign;
import feign.FeignException.FeignClientException;
import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.Request.Options;
import feign.RequestTemplate;
import feign.Response;
import feign.RetryableException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

@ExtendWith(MockitoExtension.class)
class FeignRetryTest {

    @Mock
    Client mockClient;

    @Test
    void airtableRetryTestWhenThrowError() throws IOException {
        //given
        given(mockClient.execute(any(Request.class), any(Options.class))).willThrow(new UnknownHostException());

        //when
        final KakaoApiClient kakaoApiClient = this.mockKakaoApiClient();

        //then
        assertThrows(RetryableException.class, () -> kakaoApiClient.searchBlog(KakaoReqDto.builder().build()));
        verify(mockClient, times(4)).execute(any(Request.class), any(Options.class));
    }

    @Test
    void airtableRetryTestWhenServerErrorOccurred() throws IOException {
        //given
        given(mockClient.execute(any(Request.class), any(Options.class))).willReturn(this.mockResponse(500));

        //when
        final KakaoApiClient kakaoApiClient = this.mockKakaoApiClient();
        //then
        assertThrows(RetryableException.class, () -> kakaoApiClient.searchBlog(KakaoReqDto.builder().build()));
        verify(mockClient, times(4
        )).execute(any(Request.class), any(Options.class));
    }

    @Test
    void doNotRetryWhenClientException() throws IOException {
        //given
        given(mockClient.execute(any(Request.class), any(Options.class))).willReturn(this.mockResponse(403));

        //when
        final KakaoApiClient kakaoApiClient = this.mockKakaoApiClient();

        //then
        assertThrows(FeignClientException.class, () -> kakaoApiClient.searchBlog(KakaoReqDto.builder().build()));
        verify(mockClient, times(1)).execute(any(Request.class), any(Options.class));
    }

    @Test
    void airtableRetryTestWhen5xxStatusRetry2Times() throws IOException {
        //given
        given(mockClient.execute(any(Request.class), any(Options.class)))
          .willReturn(this.mockResponse(504), this.mockResponse(200));

        //when
        final KakaoApiClient kakaoApiClient = this.mockKakaoApiClient();

        //then
        kakaoApiClient.searchBlog(KakaoReqDto.builder().build());
        verify(mockClient, times(2)).execute(any(Request.class), any(Options.class));
    }

    @Test
    void airtableRetryTestWhen5xxStatusRetry4TimesAndFinallySuccess() throws IOException {
        //given
        given(mockClient.execute(any(Request.class), any(Options.class)))
          .willReturn(this.mockResponse(500), this.mockResponse(502), this.mockResponse(504), this.mockResponse(200));

        //when
        final KakaoApiClient kakaoApiClient = this.mockKakaoApiClient();

        //then
        kakaoApiClient.searchBlog(KakaoReqDto.builder().build());
        verify(mockClient, times(4)).execute(any(Request.class), any(Options.class));
    }

    private KakaoApiClient mockKakaoApiClient() {
        return Feign.builder().client(mockClient)
          .contract(new SpringMvcContract())
          .errorDecoder(new ServerErrorDecoder())
          .retryer(new FeignExponentialBackOffRetryer())
          .target(KakaoApiClient.class, "https://dapi.kakao.com/v2/search/blog");

    }

    private Request mockRequest() {
        return Request.create(HttpMethod.GET, "https://dapi.kakao.com/v2/search/blog", Collections.emptyMap(), Body.create("test".getBytes()),
          new RequestTemplate());
    }

    private Response mockResponse(int status) {
        return Response.builder().status(status).headers(Collections.emptyMap()).request(mockRequest()).build();
    }

}