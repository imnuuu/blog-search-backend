package com.kakao.blog.service.impl;

import com.kakao.blog.dto.BlogResDto;
import com.kakao.blog.service.BlogSearchService;
import com.kakao.blog.service.BlogService;
import com.kakao.blog.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogSearchService blogSearchService;
    private final QueueService queueService;

    @Override
    public Page<BlogResDto> getBlogListByCondition(String keyword, String sort, PageRequest pageRequest) {
        queueService.sendEvent(keyword);
        return blogSearchService.getBlogListByCondition(keyword, sort, pageRequest);
    }
}
