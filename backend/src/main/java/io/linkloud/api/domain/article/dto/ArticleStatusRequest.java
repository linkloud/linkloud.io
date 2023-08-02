package io.linkloud.api.domain.article.dto;


import io.linkloud.api.domain.article.model.ReadStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleStatusRequest {

    private ReadStatus readStatus;

}
