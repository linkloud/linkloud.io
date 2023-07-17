package io.linkloud.api.domain.article.dto;

import lombok.Getter;



@Getter
public class ArticleStatusResponse {

    private final Long articleId;

    private final String articleStatus;

    public ArticleStatusResponse(Long articleId, String articleStatus) {
        this.articleId = articleId;
        this.articleStatus = articleStatus;

    }

}
