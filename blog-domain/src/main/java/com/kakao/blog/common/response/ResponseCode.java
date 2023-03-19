package com.kakao.blog.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    OK("BSK10000", "OK"),

    BAD_REQUEST("BSK40000", "BAD_REQUEST"),
    INVALID_PARAMETER("BSK40010", "INVALID_PARAMETER"),
    FORBIDDEN_REQUEST("BSK40300", "FORBIDDEN_REQUEST");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
