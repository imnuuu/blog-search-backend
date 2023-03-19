package com.kakao.blog.infrastructure.httpclient.naver.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Map;

public class NaverAuthRequestInterceptor implements RequestInterceptor {

    private static final String NAVER_CLIENT_ID="X-Naver-Client-Id";

    private static final String NAVER_CLIENT_SECRET="X-Naver-Client-Secret";

    private final String clientId;
    private final String clientSecret;

    public NaverAuthRequestInterceptor(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header(NAVER_CLIENT_ID, clientId);
        template.header(NAVER_CLIENT_SECRET, clientSecret);
    }
}
