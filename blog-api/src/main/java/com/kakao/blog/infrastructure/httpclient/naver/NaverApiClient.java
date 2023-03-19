package com.kakao.blog.infrastructure.httpclient.naver;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.kakao.blog.infrastructure.httpclient.naver.configuration.NaverClientConfiguration;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverBlog;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverReqDto;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverResDto;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
  name = "naverApis",
  url = "${external-api.naver.base-url}",
  configuration = NaverClientConfiguration.class
)
public interface NaverApiClient {

    @GetMapping(path = "/v2/search/blog", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    NaverResDto<NaverBlog> searchBlog(@SpringQueryMap @Valid NaverReqDto parameter);

}
