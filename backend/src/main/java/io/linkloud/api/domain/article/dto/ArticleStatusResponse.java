package io.linkloud.api.domain.article.dto;

import lombok.Getter;



@Getter
public class ArticleStatusResponse {

    private final Long articleId;

    private final String readStatus;

    public ArticleStatusResponse(Long articleId, String readStatus) {
        this.articleId = articleId;
        this.readStatus = readStatus;

    }

}
