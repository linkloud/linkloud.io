package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.tag.dto.TagDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ArticleResponseDto {

    private Long id;

    private String title;

    private String url;

    private String description;

    private Integer views;

    private Integer hearts;

    private List<TagDto.ArticleTagsResponse> tags;

    /**
     * Entity -> Dto
     */
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.description = article.getDescription();
        this.views = article.getViews();
        this.hearts = article.getHearts();
        this.tags = new ArrayList<>();
        if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
            article.getArticleTags().forEach(at -> this.tags.add(
                new TagDto.ArticleTagsResponse(at.getTag().getId(), at.getTag().getName())));
        }
    }
 }
