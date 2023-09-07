package io.linkloud.api.domain.article.api;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.service.ArticleServiceV2;
import io.linkloud.api.global.common.SliceResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
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
    @GetMapping
    public ResponseEntity<SliceResponse<ArticleResponseDto>> getArticles(
        @RequestParam Long lastArticleId,
        Pageable pageable,
        @RequestParam(required = false,defaultValue = "latest") SortBy sortBy) {
        Slice<ArticleResponseDto> articlesSlice = articleServiceV2.findArticlesWithNoOffset(
            lastArticleId, pageable,sortBy);
        return ResponseEntity.ok(new SliceResponse<>(articlesSlice));
    }

    // 게시글 한 개 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable("id") Long id) {
        return null;
    }

    // 게시글 생성
    @PostMapping
    public ResponseEntity<ArticleSave> createArticle(
        @RequestBody ArticleSaveRequestDto articleSaveRequestDto,
        @LoginMemberId Long memberId) {

        ArticleSave articleSave = articleServiceV2.addArticle(articleSaveRequestDto, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleSave);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long id) {
        return null;
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) {
        return null;
    }

    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchArticle() {
        return null;
    }

}
