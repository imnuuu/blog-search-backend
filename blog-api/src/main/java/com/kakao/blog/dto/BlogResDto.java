package com.kakao.blog.dto;

import com.kakao.blog.infrastructure.httpclient.kakao.dto.KakaoBlog;
import com.kakao.blog.infrastructure.httpclient.naver.dto.NaverBlog;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogResDto {

    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String thumbnail;
    private LocalDate date;

    public BlogResDto(KakaoBlog kakaoBlog){
        this.title = kakaoBlog.getTitle();
        this.contents = kakaoBlog.getContents();
        this.url = kakaoBlog.getUrl();
        this.blogName = kakaoBlog.getBlogName();
        this.thumbnail = kakaoBlog.getThumbnail();
        this.date = kakaoBlog.getDateTime().toLocalDate();
    }

    public BlogResDto(NaverBlog naverBlog){
        this.title = naverBlog.getTitle();
        this.contents = naverBlog.getDescription();
        this.url = naverBlog.getLink();
        this.blogName = naverBlog.getBloggerName();
        this.thumbnail = "";
        this.date = naverBlog.getPostDate();
    }
}
