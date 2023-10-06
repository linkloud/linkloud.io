package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleUpdateRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleListResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleUpdate;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesByCondition;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleServiceV2 {

    ArticleResponseDto getArticleById(Long id);

    ArticleSave addArticle(ArticleSaveRequestDto articleSaveRequestDto, Long memberId);

    ArticleUpdate updateArticle(ArticleUpdateRequestDto articleUpdateRequestDto,Long articleId ,Long loginMemberId);

    void deleteArticle(Long loginMemberId, Long articleId);

    //
    Slice<ArticleListResponse> findArticlesWithNoOffset(Long lastArticleId,Long loginMemberId ,Pageable pageable,
        SortBy sortBy);


    Slice<MemberArticlesByCondition> MemberArticlesByCondition(Long loginMemberId, Long memberId,
        Long lastArticleId, Pageable pageable, ReadStatus readStatus,SortBy sortBy);


    Slice<ArticleListResponse> searchArticleByKeywordOrTags(Long loginMemberId,String keyword, List<String> tags,Pageable pageable);
}
