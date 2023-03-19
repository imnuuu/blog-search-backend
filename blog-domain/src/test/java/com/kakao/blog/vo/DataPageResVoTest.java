package com.kakao.blog.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class DataPageResVoTest {

    @Test
    void getter_whenPageIsEmpty() {
        // Given
        DataPageResVo<Object> vo = new DataPageResVo(Page.empty());
        // When, Then
        assertThat(vo.getTotalCount()).isZero();
        assertThat(vo.getCurrentPageNumber()).isZero();
        assertThat(vo.getTotalPages()).isEqualTo(1);
        assertThat(vo.isFirst()).isTrue();
        assertThat(vo.isHasNext()).isFalse();
        assertThat(vo.getDataList()).isEmpty();
    }
}
