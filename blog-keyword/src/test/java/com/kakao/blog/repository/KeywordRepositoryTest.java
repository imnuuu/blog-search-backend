package com.kakao.blog.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kakao.blog.entity.Keyword;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
class KeywordRepositoryTest {

    @Autowired
    KeywordRepository keywordRepository;

    @Test
    void increaseKeywordCount() {
        //given
        Keyword test10 = keywordRepository.findByKeyword("test10")
          .orElseThrow(() -> new IllegalArgumentException("keyword does not exist"));
        //when
        keywordRepository.increaseKeywordCount(test10.getId());
        //then
        keywordRepository.findByKeyword("test10")
          .ifPresent(dto -> assertThat(dto.getCount()).isEqualTo(11L));
    }

    @Test
    void findByKeyword() {
        //give
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Order.desc("count")));

        //when
        Page<Keyword> keywords = keywordRepository.findAll(pageRequest);

        //then
        assertThat(keywords.hasNext()).isTrue();
        assertThat(keywords.getContent()).hasSize(5);
        assertThat(keywords.getContent().get(1).getCount()).isEqualTo(9L);
        assertThat(keywords.getContent().get(4).getCount()).isEqualTo(6L);

    }

    @BeforeEach
    void init(){
        keywordRepository.save(Keyword.builder().keyword("test0").count(0L).build());
        keywordRepository.save(Keyword.builder().keyword("test1").count(1L).build());
        keywordRepository.save(Keyword.builder().keyword("test2").count(2L).build());
        keywordRepository.save(Keyword.builder().keyword("test3").count(3L).build());
        keywordRepository.save(Keyword.builder().keyword("test4").count(4L).build());
        keywordRepository.save(Keyword.builder().keyword("test5").count(5L).build());
        keywordRepository.save(Keyword.builder().keyword("test6").count(6L).build());
        keywordRepository.save(Keyword.builder().keyword("test7").count(7L).build());
        keywordRepository.save(Keyword.builder().keyword("test8").count(8L).build());
        keywordRepository.save(Keyword.builder().keyword("test9").count(9L).build());
        keywordRepository.save(Keyword.builder().keyword("test10").count(10L).build());
    }
}