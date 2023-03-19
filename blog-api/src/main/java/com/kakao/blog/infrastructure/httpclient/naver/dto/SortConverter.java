package com.kakao.blog.infrastructure.httpclient.naver.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SortConverter {

    //accuracy -> sim
    //recency -> date
    public static String getSort(String sort) {
        if (StringUtils.hasText(sort) && ("recency".equals(sort) || "date".equals(sort)) ) {
            return "date";
        }
        return "sim";
    }
}
