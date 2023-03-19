package com.kakao.blog.infrastructure.httpclient.kakao;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.kakao.blog.infrastructure.httpclient.kakao.configuration.KakaoClientConfiguration;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoBlog;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoReqDto;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoResDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
  name = "kakaoApis",
  url = "${external-api.kakao.base-url}",
  configuration = KakaoClientConfiguration.class
)
public interface KakaoApiClient {
    @GetMapping(path = "/v2/search/blog", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    KakaoResDto<KakaoBlog> searchBlog(@SpringQueryMap @Valid KakaoReqDto parameter);
}
