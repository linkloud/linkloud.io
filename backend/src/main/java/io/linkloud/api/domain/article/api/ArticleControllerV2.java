package io.linkloud.api.domain.article.api;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleUpdateRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleListResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleUpdate;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.service.ArticleServiceV2;
import io.linkloud.api.global.common.SliceResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v2/articles")
@RestController
public class ArticleControllerV2 {

    private final ArticleServiceV2 articleServiceV2;



    // 게시글 목록 조회
    // TODO : 링크 목록 조회시 내 게시글 여부 (o)
    // TODO : 링크 목록 조회시 게시글 읽음 상태 (O)
    @GetMapping
    public ResponseEntity<SliceResponse<ArticleListResponse>> getArticles(
        @RequestParam(required = false) Long nextId,
        @LoginMemberId(required = false) Long loginMemberId,
        Pageable pageable,
        @RequestParam(required = false,defaultValue = "latest") SortBy sortBy) {
        Slice<ArticleListResponse> articlesSlice = articleServiceV2.findArticlesWithNoOffset(
            nextId,loginMemberId, pageable,sortBy);
        return ResponseEntity.ok(new SliceResponse<>(articlesSlice));
    }

    // 게시글 한 개 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable("id") Long id) {
        ArticleResponseDto getArticle = articleServiceV2.getArticleById(id);
        return ResponseEntity.ok(getArticle);
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<ArticleSave> createArticle(
        @RequestBody ArticleSaveRequestDto articleSaveRequestDto,
        @LoginMemberId Long loginMemberId
        ) {
        ArticleSave articleSave = articleServiceV2.addArticle(articleSaveRequestDto, loginMemberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleUpdate> updateArticle(
        @PathVariable("id") Long articleId,
        @LoginMemberId Long loginMemberId,
        @RequestBody @Valid ArticleUpdateRequestDto updateRequestDto) {
        ArticleUpdate articleUpdate = articleServiceV2.updateArticle(updateRequestDto, articleId,
            loginMemberId);

        return ResponseEntity.ok(articleUpdate);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(
        @LoginMemberId Long loginMemberId,
        @PathVariable("id") Long articleId) {
        articleServiceV2.deleteArticle(loginMemberId, articleId);
        return ResponseEntity.noContent().build();
    }

    // 게시글 키워드로 검색 or 태그로 검색
    // TODO : 링크 목록 조회시 내 게시글 여부 (o)
    @GetMapping("/search")
    public ResponseEntity<SliceResponse<ArticleListResponse>> searchArticleByKeywordOrTags(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) List<String> tags,
        @LoginMemberId(required = false) Long loginMemberId,
        Pageable pageable) {

        Slice<ArticleListResponse> searchResponse = articleServiceV2.searchArticleByKeywordOrTags(
            loginMemberId,keyword, tags, pageable);

        return ResponseEntity.ok(new SliceResponse<>(searchResponse));
    }

}
