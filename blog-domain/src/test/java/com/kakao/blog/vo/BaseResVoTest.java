package com.kakao.blog.vo;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.blog.common.response.ResponseCode;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class BaseResVoTest {

    @Test
    void getter() {
        // Given
        BaseResVo baseResVo = new BaseResVo(ResponseCode.OK);
        // When, Then
        assertThat(baseResVo.getResultCode()).isEqualTo("BSK10000");
        assertThat(baseResVo.getMessage()).isEqualTo("OK");
    }
}
