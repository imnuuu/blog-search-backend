package com.kakao.blog.infrastructure.httpclient.kakao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class KakaoBlog {

    @JsonProperty("title")
    private String title;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("url")
    private String url;
    @JsonProperty("blogname")
    private String blogName;
    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime dateTime;

}
