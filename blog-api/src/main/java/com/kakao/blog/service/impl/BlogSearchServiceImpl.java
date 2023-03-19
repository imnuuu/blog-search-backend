package com.kakao.blog.service.impl;

import com.kakao.blog.dto.BlogResDto;
import com.kakao.blog.infrastructure.httpclient.kakao.KakaoApiClient;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoBlog;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoReqDto;
import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoResDto;
import com.kakao.blog.infrastructure.httpclient.naver.NaverApiClient;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverBlog;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverReqDto;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverResDto;
import com.kakao.blog.service.BlogSearchService;
import feign.RetryableException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogSearchServiceImpl implements BlogSearchService {

    private final KakaoApiClient kakaoApiClient;
    private final NaverApiClient naverApiClient;

    @Override
    public Page<BlogResDto> getBlogListByCondition(String keyword, String sort, PageRequest pageRequest) {
        try {
            KakaoResDto<KakaoBlog> kakaoResDto = kakaoApiClient.searchBlog(KakaoReqDto.builder()
              .query(keyword)
              .page(pageRequest.getPageNumber() + 1)
              .size(pageRequest.getPageSize())
              .sort(sort)
              .build());

            return new PageImpl<>(kakaoResDto.getDocuments().stream().map(BlogResDto::new).collect(Collectors.toList()), pageRequest, kakaoResDto.getMeta().getTotalCount());
        } catch (RetryableException retryableException) {
            NaverResDto<NaverBlog> naverResDto = naverApiClient.searchBlog(NaverReqDto.builder()
              .query(keyword)
              .start(pageRequest.getPageNumber() + 1)
              .display(pageRequest.getPageSize())
              .sort(sort)
              .build());

            return new PageImpl<>(naverResDto.getItems().stream().map(BlogResDto::new).collect(Collectors.toList()), pageRequest,
              naverResDto.getTotal());
        }
    }
}
