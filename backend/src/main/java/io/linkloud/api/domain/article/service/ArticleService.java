package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    /** 아티클 생성 */
    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
        Article createdArticle = articleRepository.save(requestDto.toAriticleEntity());  // requestDto를 엔티티로 변환.

        return new ArticleResponseDto(createdArticle);
    }

}
