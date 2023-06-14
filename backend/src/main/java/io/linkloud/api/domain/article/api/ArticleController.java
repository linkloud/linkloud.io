package io.linkloud.api.domain.article.api;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.service.ArticleService;
import io.linkloud.api.global.common.MultiDataResponse;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
@Validated
public class ArticleController {

    private final ArticleService articleService;

    /** 아티클 모두 조회 */
    @GetMapping
    public ResponseEntity<MultiDataResponse<ArticleResponseDto>> getAllArticle(@Positive @RequestParam int page) {
        Page<ArticleResponseDto> getAllArticleDto = articleService.fetchAllArticle(page);

        return ResponseEntity.ok(new MultiDataResponse<>(getAllArticleDto));
    }

    /** 아티클 한 개 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<SingleDataResponse<ArticleResponseDto>> getOneArticle(@PathVariable @Valid Long id) {
        ArticleResponseDto getOneArticleDto = articleService.fetchArticleById(id);

        return ResponseEntity.ok(new SingleDataResponse<>(getOneArticleDto));
    }

    /** 아티클 작성 */
    @PostMapping
    public ResponseEntity<SingleDataResponse<ArticleResponseDto>> createArticle(
        @LoginMemberId Long memberId,
        @RequestBody @Valid ArticleRequestDto articleRequestDto) {

        ArticleResponseDto createdArticleDto = articleService.addArticle(memberId, articleRequestDto);

        return ResponseEntity.ok(new SingleDataResponse<>(createdArticleDto));

    }

    /** 아티클 수정 */
    // PutMapping   : 해당 리소스를 대체하는 메소드
    // PatchMapping : 리소스의 일부를 바꾸는 메소드
    @PatchMapping("/{articleId}")
    public ResponseEntity<SingleDataResponse<ArticleResponseDto>> patchArticle(@PathVariable Long articleId, @LoginMemberId Long memberId, @RequestBody @Valid ArticleUpdateDto articleUpdateDto) {
        ArticleResponseDto updatedArticleDto = articleService.updateArticle(articleId, memberId, articleUpdateDto);

        return ResponseEntity.ok(new SingleDataResponse<>(updatedArticleDto));

    }

    /** 아티클 삭제 */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void>deleteArticle(@PathVariable @Valid Long articleId ,@LoginMemberId Long memberId) {
        articleService.removeArticle(articleId, memberId);

        return ResponseEntity.noContent().build();
    }

    /** 검색 */
    @GetMapping("search")
    public ResponseEntity<MultiDataResponse> getArticleBySearch(
        @RequestParam String keyword,
        @RequestParam String keywordType,
        @Positive @RequestParam int page) {

        /* 키워드 목록
         * title               : 제목
         * description         : 내용
         * titleAndDescription : 제목 + 내용
         */
        Page<ArticleResponseDto> getArticleBySearchDto = articleService.fetchArticleBySearch(keyword, keywordType, page);

        return ResponseEntity.ok(new MultiDataResponse<>(getArticleBySearchDto));
    }

}
