package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    /** 아티클 모두 반환 */
    @Transactional(readOnly = true)
    public List<ArticleResponseDto> getAllArticle() {
        List<ArticleResponseDto> articleDtoList = articleRepository.findAll()
            .stream()
            .map(ArticleResponseDto::new)
            .collect(Collectors.toList());  // stream의 결과를 List로 수집

        return articleDtoList;
    }

    /** 아티클 한 개 반환 */
    @Transactional
    public ArticleResponseDto getArticleById(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아티클이 없습니다."));
        foundedArticle.articleViewIncrease(foundedArticle.getViews() + 1);  // 조회수 증가

        return new ArticleResponseDto(foundedArticle);
    }

    /** 아티클 생성 */
    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
        Article createdArticle = articleRepository.save(requestDto.toAriticleEntity(memberRepository));  // requestDto를 엔티티로 변환.

        return new ArticleResponseDto(createdArticle);
    }

    /** 아티클 수정 */
    @Transactional
    public ArticleResponseDto updateArticle(Long id, ArticleRequestDto requestDto) {
        Article updatedArticle = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아티클이 없습니다."));   // 수정할 아티클 조회
        updatedArticle.articleUpdate(requestDto);

        return new ArticleResponseDto(updatedArticle);
    }

    /** 아티클 삭제 */
    @Transactional
    public void deleteArticle(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 아티클이 없습니다."));
        articleRepository.delete(foundedArticle);
    }

}