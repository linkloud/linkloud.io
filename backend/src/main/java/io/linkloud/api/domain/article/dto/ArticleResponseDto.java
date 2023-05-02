package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ArticleResponseDto {

    private Long id;

    private Long member_id;

    private String member_nickname;

    private String title;

    private String url;

    private String description;

    private Integer views;

    private Integer bookmarks;


    /** Entity -> Dto */
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.member_id = article.getMember().getId();    // article.getMember_id()에서 Member형의 member_id를 가져온 후, Member 아티클의 getter를 이용.
        this.member_nickname = article.getMember().getNickname();
        this.title = article.getTitle();
        this.url = article.getUrl();
        this.description = article.getDescription();
        this.views = article.getViews();
        this.bookmarks = article.getBookmarks();
    }

}
