package com.kakao.blog.infrastructure.httpclient.kakao.dto;


import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoReqDto {
    @NotBlank
    private String query;
    private String sort;
    private Integer page;
    private Integer size;

    public static KakaoReqDto of(String query) {
        return builder().query(query).build();
    }

}
