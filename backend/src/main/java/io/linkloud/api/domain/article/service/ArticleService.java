package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static io.linkloud.api.global.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
@Validated
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;


    /** 아티클 모두 반환 */
    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> fetchAllArticle(int page) {
        Page<Article> articlesPage = articleRepository.findAll(PageRequest.of(page - 1, 10, Sort.by("createdAt").descending()));

        return articlesPage.map(article -> new ArticleResponseDto(article));
    }

    /** 아티클 한 개 반환 */
    @Transactional
    public ArticleResponseDto fetchArticleById(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new LogicException(TEMPORARY_ERROR));
        foundedArticle.articleViewIncrease(foundedArticle.getViews() + 1);  // 조회수 증가

        return new ArticleResponseDto(foundedArticle);
    }

    /** 아티클 생성 */
    @Transactional
    public ArticleResponseDto addArticle(ArticleRequestDto requestDto) {
        Member foundedMember = memberRepository.findById(requestDto.getMember_id()).orElseThrow(() -> new LogicException(ExceptionCode.MEMBER_NOT_FOUND));
        Article createdArticle = articleRepository.save(requestDto.toArticleEntity(foundedMember));  // requestDto를 엔티티로 변환.

        return new ArticleResponseDto(createdArticle);
    }

    /** 아티클 수정 */
    @Transactional
    public ArticleResponseDto updateArticle(Long id, ArticleUpdateDto updateDto) {
        Article updatedArticle = articleRepository.findById(id).orElseThrow(() -> new LogicException(TEMPORARY_ERROR));   // 수정할 아티클 조회
        updatedArticle.articleUpdate(updateDto);

        return new ArticleResponseDto(updatedArticle);
    }

    /** 아티클 삭제 */
    @Transactional
    public void removeArticle(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new LogicException(TEMPORARY_ERROR));
        articleRepository.delete(foundedArticle);
    }

}
