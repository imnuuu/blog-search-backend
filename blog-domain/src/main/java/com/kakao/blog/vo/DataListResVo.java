package com.kakao.blog.vo;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class DataListResVo<T> extends BaseResVo {

    private final int count;
    private final List<T> dataList;

    @Builder
    public DataListResVo(List<T> dataList) {
        super();
        this.count = dataList.size();
        this.dataList = dataList;
    }

}
