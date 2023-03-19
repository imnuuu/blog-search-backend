package com.kakao.blog.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kakao.blog.entity.Keyword;
import com.kakao.blog.repository.KeywordRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@RabbitAvailable(queues = "blog-keyword")
class KeywordServiceTest {

    @MockBean
    KeywordRepository keywordRepository;

    @Autowired
    KeywordService keywordService;

    @Test
    void increaseKeywordCount() {

        //given
        given(keywordRepository.findByKeyword("test")).willReturn(Optional.empty());
        given(keywordRepository.save(any(Keyword.class))).willReturn(this.getKeyword(1L, "test"));

        //when
        keywordService.increaseKeywordCount("test");

        //then
        verify(keywordRepository, times(1)).findByKeyword("test");
        verify(keywordRepository, times(1)).save(any(Keyword.class));
        verify(keywordRepository, times(1)).increaseKeywordCount(1L);

    }

    @Test
    void findTopNKeywords() {
        //given
        int limit = 10;
        PageRequest pageRequest = PageRequest.of(0, limit, Sort.by(Order.desc("count")));
        given(keywordRepository.findAll(pageRequest)).willReturn(
          new PageImpl<>(List.of(
            this.getKeyword(10L, "test10"),
            this.getKeyword(9L, "test9"),
            this.getKeyword(8L, "test8"),
            this.getKeyword(7L, "test7"),
            this.getKeyword(6L, "test6"),
            this.getKeyword(5L, "test5"),
            this.getKeyword(4L, "test4"),
            this.getKeyword(3L, "test3"),
            this.getKeyword(2L, "test2"),
            this.getKeyword(1L, "test1"))
            , pageRequest, 30));
        //when
        Optional<List<Keyword>> topNKeywords = keywordService.findTopNKeywords(10);

        //then
        assertThat(topNKeywords).isPresent();
        assertThat(topNKeywords.get()).hasSize(10);
        assertThat(topNKeywords.get().get(0).getCount()).isEqualTo(10L);
    }

    private Keyword getKeyword(Long id, String keyword){
        Keyword result = Keyword.builder().keyword(keyword).count(id).build();
        result.setId(id);
        return result;
    }
}