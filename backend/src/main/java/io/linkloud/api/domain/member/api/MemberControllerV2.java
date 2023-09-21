package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticleStatusResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse.MemberArticlesByReadStatus;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.service.ArticleServiceV2;
import io.linkloud.api.domain.member.service.MemberArticleStatusService;
import io.linkloud.api.global.common.SliceResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequestMapping("/api/v2/member")
@RequiredArgsConstructor
@RestController
public class MemberControllerV2 {

    private final ArticleServiceV2 articleServiceV2;
    private final MemberArticleStatusService memberArticleStatusService;

    // 내가 작성한 게시글 목록 인기순,최신순 조회
    @GetMapping("/{memberId}/articles")
    public ResponseEntity<SliceResponse<MemberArticlesSortedResponse>> getArticles(
        @RequestParam(required = false) Long nextId,
        Pageable pageable,
        @RequestParam SortBy sortBy,
        @LoginMemberId Long loginMemberId,
        @PathVariable Long memberId) {
        Slice<MemberArticlesSortedResponse> myArticles = articleServiceV2.findArticlesByMemberSorted(
            loginMemberId, memberId, nextId, pageable, sortBy);
        return ResponseEntity.ok(new SliceResponse<>(myArticles));
    }

    // 내가 변경한 다른 사람의 게시글 상태로 조회
    @GetMapping("/{memberId}/read-status")
    public ResponseEntity<SliceResponse<MemberArticlesByReadStatus>> getMemberArticlesByStatus(
        @RequestParam(required = false) Long nextId,
        Pageable pageable,
        @LoginMemberId Long loginMemberId,
        @PathVariable Long memberId,
        @RequestParam ReadStatus readStatus) {
        Slice<MemberArticlesByReadStatus> myArticles = articleServiceV2.findArticlesByReadStatus(loginMemberId,
            memberId, nextId, pageable, readStatus);
        return ResponseEntity.ok(new SliceResponse<>(myArticles));
    }

    // 게시글 상태 변경
    @PatchMapping("/{memberId}/articles/{articleId}/status")
    public ResponseEntity<MemberArticleStatusResponse> updateArticleStatusByMember(
        @PathVariable Long memberId,
        @PathVariable Long articleId,
        @LoginMemberId Long loginMemberId,
        @RequestParam ReadStatus readStatus) {
        MemberArticleStatusResponse newArticleStatusByMember = memberArticleStatusService.setArticleStatus(
            memberId, articleId, loginMemberId, readStatus);
        return ResponseEntity.ok(newArticleStatusByMember);
    }

}
