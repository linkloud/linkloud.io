package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ArticleResponseDto {

    @NotBlank(message = "id 값이 없습니다.")
    private Long id;

    @NotBlank(message = "member_id 값이 없습니다.")
    private Member member_id;

    @NotBlank(message = "title 값이 없습니다.")
    private String title;

    @NotBlank(message = "url 값이 없습니다.")
    private String url;

    @NotBlank(message = "description 값이 없습니다.")
    private String description;

    @NotBlank(message = "views 값이 없습니다.")
    private Integer views;

    @NotBlank(message = "bookmarks 값이 없습니다.")
    private Integer bookmarks;


    /** Entity -> Dto */
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.member_id = article.getMember_id();
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.description = article.getDescription();
        this.views = article.getViews();
        this.bookmarks = article.getBookmarks();
    }

}
