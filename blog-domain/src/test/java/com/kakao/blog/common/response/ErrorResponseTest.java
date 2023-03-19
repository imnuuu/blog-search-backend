package com.kakao.blog.common.response;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ErrorResponseTest {
    @Test
    void defaultConstructor() {
        // Given
        ErrorResponse<String> response = new ErrorResponse<>(ResponseCode.BAD_REQUEST);
        // When
        assertThat(response.getResultCode()).isEqualTo("BSK40000");
        assertThat(response.getMessage()).isEqualTo("BAD_REQUEST");
    }

}