package com.kakao.blog.controller;

import com.kakao.blog.dto.BlogResDto;
import com.kakao.blog.service.BlogService;
import com.kakao.blog.vo.DataPageResVo;
import java.io.UnsupportedEncodingException;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataPageResVo<BlogResDto> searchBlog(
      @RequestParam @NotBlank String keyword, @RequestParam(defaultValue = "accuracy") String sort,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws UnsupportedEncodingException {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new DataPageResVo<>(blogService.getBlogListByCondition(keyword.trim(), sort, pageRequest));
    }

}
