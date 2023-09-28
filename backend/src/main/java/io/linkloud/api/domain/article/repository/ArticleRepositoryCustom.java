package io.linkloud.api.domain.article.repository;

import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleListResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse.MemberArticlesByReadStatus;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.member.model.Member;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ArticleRepositoryCustom {
    // 목록 조회 : 검색
    Page<ArticleResponseDto> findArticleListBySearch(String keyword, List<String> tags, Pageable pageable);

    Page<MyArticlesResponseDto> findMyArticleByTag(Member m, String t, String articleStatus,
        Pageable pageable);

    // 게시글 목록 : 무한 스크롤
    Slice<ArticleListResponse> findArticlesWithNoOffset(Long lastArticleId, Pageable pageable,
        SortBy sortBy);

    // 게시글 최신순,인기순 정렬
    Slice<MemberArticlesSortedResponse> findArticlesByMemberSorted(Long memberId,Long lastArticleId, Pageable pageable,
        SortBy sortBy);

    // 게시글 상태순 정렬
    Slice<MemberArticlesByReadStatus> findArticlesByReadStatus(Long memberId, Long lastArticleId,
        Pageable pageable, ReadStatus readStatus);

    // 게시글 검색어, tags 로 검색
    Slice<ArticleResponseDto> findArticlesByKeywordOrTags(String keyword, List<String> tags, Pageable pageable);
}
