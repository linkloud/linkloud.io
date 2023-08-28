package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;

public interface ArticleServiceV2 {

    ArticleSave addArticle(ArticleSaveRequestDto articleSaveRequestDto,Long memberId);


}
