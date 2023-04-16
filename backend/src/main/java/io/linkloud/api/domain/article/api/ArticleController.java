package io.linkloud.api.domain.article.api;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.service.ArticleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /** 아티클 모두 조회 */
    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAllArticle() {
        List<ArticleResponseDto> getAllArticleDto = articleService.getAllArticle();

        return new ResponseEntity<>(getAllArticleDto, HttpStatus.OK);
    }

    /** 아티클 한 개 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getOneArticle(@PathVariable @Valid Long id) {
        ArticleResponseDto getOneArticleDto = articleService.getArticleById(id);

        return new ResponseEntity<>(getOneArticleDto, HttpStatus.OK);
    }

    /** 아티클 작성 */
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody @Valid ArticleRequestDto articleRequestDto) {
        ArticleResponseDto createdArticleDto = articleService.createArticle(articleRequestDto);

        return new ResponseEntity<>(createdArticleDto, HttpStatus.OK);
    }

}
