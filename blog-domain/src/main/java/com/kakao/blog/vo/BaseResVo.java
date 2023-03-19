package com.kakao.blog.vo;

import com.kakao.blog.common.response.ResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class BaseResVo {

    private final String resultCode;
    private final String message;

    public BaseResVo(ResponseCode responseCode) {
        this.resultCode = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public BaseResVo() {
        this.resultCode = ResponseCode.OK.getCode();
        this.message = ResponseCode.OK.getMessage();
    }

}
