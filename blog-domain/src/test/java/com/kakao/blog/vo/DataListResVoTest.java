package com.kakao.blog.vo;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class DataListResVoTest {

    @Test
    void builder() {
        // Given
        DataListResVo<Object> vo = DataListResVo.builder().dataList(List.of()).build();
        // When, Then
        assertThat(vo.getCount()).isZero();
        assertThat(vo.getDataList()).isEmpty();
    }
}
