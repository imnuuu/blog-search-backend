package com.kakao.blog.infrastructure.httpclient.naver.configuration;

import com.kakao.blog.common.FeignExponentialBackOffRetryer;
import com.kakao.blog.common.JacksonIgnoreNullEncoder;
import com.kakao.blog.common.ServerErrorDecoder;
import com.kakao.blog.infrastructure.httpclient.kakao.configuration.KakaoAuthRequestInterceptor;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class NaverClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public RequestInterceptor bearerAuthRequestInterceptor(@Value("${external-api.kakao.api-key}") String apiKey) {
        return new KakaoAuthRequestInterceptor(apiKey);
    }

    @Bean
    public Encoder encoder() {
        return new JacksonIgnoreNullEncoder();
    }

    @Bean
    public Retryer retryer() {
        return new FeignExponentialBackOffRetryer();
    }

    @Bean
    public ErrorDecoder decoder() {
        return new ServerErrorDecoder();
    }
}
