package com.kakao.blog.vo;

import com.kakao.blog.common.response.ResponseCode;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DataPageResVo<T> extends BaseResVo {

    private final long totalCount;
    private final int currentPageNumber;
    private final int totalPages;
    private final boolean isFirst;
    private final boolean hasNext;
    private final List<T> dataList;

    public DataPageResVo(Page<T> pageObject) {
        super();
        this.totalCount = pageObject.getTotalElements();
        this.currentPageNumber = pageObject.getNumber();
        this.totalPages = pageObject.getTotalPages();
        this.isFirst = pageObject.isFirst();
        this.hasNext = pageObject.hasNext();
        this.dataList = pageObject.getContent();
    }

    @Builder
    public DataPageResVo(ResponseCode responseCode, Page<T> pageObject) {
        super(responseCode);
        this.totalCount = pageObject.getTotalElements();
        this.currentPageNumber = pageObject.getNumber();
        this.totalPages = pageObject.getTotalPages();
        this.isFirst = pageObject.isFirst();
        this.hasNext = pageObject.hasNext();
        this.dataList = pageObject.getContent();
    }

}
