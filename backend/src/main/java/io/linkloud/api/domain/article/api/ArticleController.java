package io.linkloud.api.domain.article.api;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.service.ArticleService;
import io.linkloud.api.global.common.MultiDataResponse;
import io.linkloud.api.global.common.SingleDataResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    /** 아티클 모두 조회 */
    @GetMapping
    public MultiDataResponse getAllArticle() {
        List<ArticleResponseDto> getAllArticleDto = articleService.getAllArticle();
        Page<ArticleResponseDto> page = articleService.getPage();

        return new MultiDataResponse(getAllArticleDto, page);
    }

    /** 아티클 한 개 조회 */
    @GetMapping("/{id}")
    public SingleDataResponse<ArticleResponseDto> getOneArticle(@PathVariable @Valid Long id) {
        ArticleResponseDto getOneArticleDto = articleService.getArticleById(id);

        return new SingleDataResponse<>(getOneArticleDto);
    }

    /** 아티클 작성 */
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody @Valid ArticleRequestDto articleRequestDto) {
        ArticleResponseDto createdArticleDto = articleService.createArticle(articleRequestDto);

        return new ResponseEntity<>(createdArticleDto, HttpStatus.OK);
    }

    /** 아티클 수정 */
    // PutMapping   : 해당 리소스를 대체하는 메소드
    // PatchMapping : 리소스의 일부를 바꾸는 메소드
    @PatchMapping("{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id, @RequestBody @Valid ArticleUpdateDto articleUpdateDto) {
        ArticleResponseDto updatedArticleDto = articleService.updateArticle(id, articleUpdateDto);

        return new ResponseEntity<>(updatedArticleDto, HttpStatus.OK);
    }

    /** 아티클 삭제 */
    @DeleteMapping("{id}")
    public ResponseEntity<ArticleResponseDto> deleteArticle(@PathVariable @Valid Long id) {
        articleService.deleteArticle(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
