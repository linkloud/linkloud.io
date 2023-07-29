package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.ReadStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyArticlesResponseDto {

    private Long id;

    private Long memberId;

    private String memberNickname;

    private String title;

    private String url;

    private String description;

    private Integer views;

    private Integer bookmarks;

    private List<String> tags;
    private final ReadStatus readStatus;




    public MyArticlesResponseDto(Article article) {
        this.id = article.getId();
        this.memberId = article.getMember().getId();
        this.memberNickname = article.getMember().getNickname();
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.description = article.getDescription();
        this.views = article.getViews();
        this.bookmarks = article.getBookmarks();
        this.tags = new ArrayList<>();
        if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
            article.getArticleTags().forEach(at -> this.tags.add(at.getTag().getName()));
        }
        this.readStatus = article.getReadStatus();
    }

}
