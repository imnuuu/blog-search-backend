package com.kakao.blog.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public final class ErrorResponse<T> {
    private String resultCode;
    private String message;
    private T data;

    public ErrorResponse(ResponseCode resultCode) {
        this.resultCode = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ErrorResponse(ResponseCode resultCode, T data) {
        this.resultCode = resultCode.getCode();
        this.data = data;
    }

    public static ErrorResponse<String> of(ResponseCode responseCode){
        return new ErrorResponse<>(responseCode);
    }

    public static ErrorResponse<String> of(ResponseCode responseCode, String detail){
        return new ErrorResponse<>(responseCode, detail);
    }
}
