package io.linkloud.api.domain.member.api;


import io.linkloud.api.domain.article.dto.ArticleStatusRequest;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.article.dto.ArticleStatusResponse;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.domain.tag.dto.MemberTagsDto;
import io.linkloud.api.global.common.MultiDataResponse;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<SingleDataResponse<MemberLoginResponse>> findMe(
            @NonNull @LoginMemberId Long loginMemberId) {
        MemberLoginResponse memberLoginResponse = memberService.fetchPrincipal(loginMemberId);
        return ResponseEntity.ok(new SingleDataResponse<>(memberLoginResponse));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
            @LoginMemberId @NonNull Long loginMemberId,
            @RequestBody @Valid MemberNicknameRequestDto requestNicknameDto
    ) {
        memberService.updateNickname(loginMemberId, requestNicknameDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{memberId}/articles")
    public ResponseEntity getMyArticles(
            @PathVariable Long memberId,
            @LoginMemberId Long extractedMemberId,
            @RequestParam(defaultValue = "createdAt") SortBy sortBy,
            @RequestParam(required = false) String tag,
            @Positive @RequestParam int page
    ) {
        Page<MyArticlesResponseDto> articleResponseDto =
                memberService.fetchMyArticles(memberId, extractedMemberId, sortBy.getSortBy(), tag, page);
        return ResponseEntity.ok(new MultiDataResponse(articleResponseDto));
    }

    @PatchMapping("{memberId}/article-status/{articleId}")
    public ResponseEntity<SingleDataResponse<ArticleStatusResponse>> updateMyArticlesStatus(
            @PathVariable Long memberId,
            @PathVariable Long articleId,
            @LoginMemberId Long extractedMemberId,
            @RequestBody ArticleStatusRequest articleStatusRequest) {
        ArticleStatusResponse article = memberService.updateMyArticleStatus(memberId, extractedMemberId, articleId, articleStatusRequest);
        return ResponseEntity.ok(new SingleDataResponse<>(article));
    }

    @GetMapping("/{memberId}/tags")
    public ResponseEntity<MultiDataResponse<MemberTagsDto>> getMyTags(
        @PathVariable Long memberId,
        @LoginMemberId Long extractedMemberId,
        @Positive @RequestParam int page
    ) {
        Page<MemberTagsDto> responses = memberService.fetchMemberTags(memberId,extractedMemberId,page);
        return ResponseEntity.ok(new MultiDataResponse<>(responses));
    }
}
