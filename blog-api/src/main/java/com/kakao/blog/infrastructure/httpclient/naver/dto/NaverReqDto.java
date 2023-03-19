package com.kakao.blog.infrastructure.httpclient.naver.dto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NaverReqDto {
    @NotBlank
    private final String query;

    private final Integer display;
    private final Integer start;
    private final String sort;

    @Builder
    public NaverReqDto(String query, Integer display, Integer start, String sort) {
        this.query = URLEncoder.encode(query, StandardCharsets.UTF_8);
        this.display = display;
        this.start = start;
        this.sort = SortConverter.getSort(sort);
    }
}
