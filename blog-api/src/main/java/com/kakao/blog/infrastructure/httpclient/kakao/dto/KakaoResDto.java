package com.kakao.blog.infrastructure.httpclient.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoResDto<T> {

    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<T> documents;

}
