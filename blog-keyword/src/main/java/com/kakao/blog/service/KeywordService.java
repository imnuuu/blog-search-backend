package com.kakao.blog.service;

import com.kakao.blog.entity.Keyword;
import java.util.List;
import java.util.Optional;

public interface KeywordService {

    void increaseKeywordCount(String keyword);

    Optional<List<Keyword>> findTopNKeywords(int limit);

}
