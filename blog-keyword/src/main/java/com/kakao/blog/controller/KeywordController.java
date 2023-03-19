package com.kakao.blog.controller;

import com.kakao.blog.entity.Keyword;
import com.kakao.blog.service.KeywordService;
import com.kakao.blog.vo.DataListResVo;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/keyword")
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping(path = "/top", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DataListResVo<Keyword> searchTopNKeyword(@RequestParam(defaultValue = "10") int limit){
        return new DataListResVo<>(keywordService.findTopNKeywords(limit).orElse(Collections.emptyList()));
    }
}
