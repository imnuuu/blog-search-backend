package com.kakao.blog.infrastructure.httpclient.kakao.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class KakaoAuthRequestInterceptor implements RequestInterceptor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String KAKAO_AK = "KakaoAK";

    private final String apiKey;

    public KakaoAuthRequestInterceptor(String apiKey) {this.apiKey = apiKey;}

    @Override
    public void apply(RequestTemplate template) {
        template.header(AUTHORIZATION, String.format("%s %s", KAKAO_AK, apiKey));
    }
}
