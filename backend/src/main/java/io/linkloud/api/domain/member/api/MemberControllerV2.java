package io.linkloud.api.domain.member.api;

import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticleStatusResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesByCondition;
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


    // 1. 게시글 상태조회를 파라매터로 받는다
    // 2. 인기순,최신순,게시글 상태순 조건순으로 정렬
    // 내가 작성한 게시글 목록 인기순,최신순 조회
    // TODO : 둘중 한개는 무조건 필수
    @GetMapping("/{memberId}/articles")
    public ResponseEntity<SliceResponse<MemberArticlesByCondition>> getArticlesByCondition(
        @RequestParam(required = false) Long nextId,
        Pageable pageable,
        @LoginMemberId(required = false) Long loginMemberId,
        @RequestParam String sortBy,
        @PathVariable Long memberId) {
        Slice<MemberArticlesByCondition> memberArticlesByCondition = articleServiceV2.MemberArticlesByCondition(loginMemberId, memberId,
            nextId, pageable, sortBy);
        return ResponseEntity.ok(new SliceResponse<>(memberArticlesByCondition));
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
