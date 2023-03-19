package com.kakao.blog.dto;

import com.kakao.blog.entity.Keyword;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsExclude;

@EqualsAndHashCode
@Getter
@ToString
public class KeywordResDto {
    private Long id;
    private String keyword;
    @EqualsExclude
    private Long count;
    @EqualsExclude
    private LocalDateTime createAt;
    @EqualsExclude
    private LocalDateTime updateAt;

    public KeywordResDto(Keyword keyword){
        this.id = keyword.getId();
        this.keyword = keyword.getKeyword();
        this.count = keyword.getCount();
        this.createAt = keyword.getCreateAt();
        this.updateAt = keyword.getUpdateAt();
    }
}
