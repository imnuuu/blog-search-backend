package com.kakao.blog.service;

import com.kakao.blog.dto.BlogResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BlogSearchService {

    Page<BlogResDto> getBlogListByCondition(String keyword, String sort, PageRequest pageRequest) ;

}
