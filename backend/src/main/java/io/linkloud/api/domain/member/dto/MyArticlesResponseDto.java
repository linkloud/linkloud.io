package io.linkloud.api.domain.member.dto;

import io.linkloud.api.domain.article.model.Article;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyArticlesResponseDto {

    private final String title;

    private final String url;

    private final String description;

    private final List<String> tags;





    public MyArticlesResponseDto(Article article) {
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.description = article.getDescription();
        this.tags = new ArrayList<>();
        if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
            article.getArticleTags().forEach(at -> this.tags.add(at.getTag().getName()));
        }
    }

}
