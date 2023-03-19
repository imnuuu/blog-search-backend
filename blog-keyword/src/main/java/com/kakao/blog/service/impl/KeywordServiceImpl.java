package com.kakao.blog.service.impl;

import com.kakao.blog.entity.Keyword;
import com.kakao.blog.repository.KeywordRepository;
import com.kakao.blog.service.KeywordService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    @Override
    public void increaseKeywordCount(String keyword) {
        Keyword keywordEntity = keywordRepository.findByKeyword(keyword)
          .orElseGet(() -> keywordRepository.save(Keyword.builder().keyword(keyword).count(0L).build()));

        keywordRepository.increaseKeywordCount(keywordEntity.getId());
    }

    @Override
    public Optional<List<Keyword>> findTopNKeywords(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Order.desc("count")));
        return Optional.of(keywordRepository.findAll(pageRequest).getContent().stream().collect(Collectors.toList()));
    }
}
