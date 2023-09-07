package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse.MemberArticlesByReadStatus;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleServiceV2 {

    ArticleSave addArticle(ArticleSaveRequestDto articleSaveRequestDto, Long memberId);

    Slice<ArticleResponseDto> findArticlesWithNoOffset(Long lastArticleId, Pageable pageable,
        SortBy sortBy);

    Slice<MemberArticlesSortedResponse> findArticlesByMemberSorted(Long loginMemberId, Long memberId,
        Long lastArticleId, Pageable pageable, SortBy sortBy);

    Slice<MemberArticlesByReadStatus> findArticlesByReadStatus(Long loginMemberId, Long memberId, Long lastArticleId,
        Pageable pageable,ReadStatus readStatus);


}
