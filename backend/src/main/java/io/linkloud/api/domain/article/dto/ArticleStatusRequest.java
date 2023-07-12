package io.linkloud.api.domain.article.dto;


import io.linkloud.api.domain.article.model.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleStatusRequest {

    private ArticleStatus articleStatus;

}
