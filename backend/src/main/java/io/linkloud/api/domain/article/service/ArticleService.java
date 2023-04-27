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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.linkloud.api.global.exception.ExceptionCode.*;

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

    /** 페이지 정보 반환 */
    @Transactional
    public Page<ArticleResponseDto> getPage(){
        List<ArticleResponseDto> articleDtoList = getAllArticle();
        Page page = new PageImpl(
            /* 객체를 담고 있는 리스트
             * 페이지 표현 정보 : 0 페이지에서 10개씩 데이터를 가져옴, createdAt 필드를 기준으로 내림차순 정렬
             * 전체 데이터(아티클)의 개수 */
            articleDtoList,
            PageRequest.of(0, 10, Sort.by("createdAt").descending()),
            articleDtoList.size());

        return page;
    }

    /** 아티클 한 개 반환 */
    @Transactional
    public ArticleResponseDto getArticleById(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new LogicException(TEMPORARY_ERROR));
        foundedArticle.articleViewIncrease(foundedArticle.getViews() + 1);  // 조회수 증가

        return new ArticleResponseDto(foundedArticle);
    }

    /** 아티클 생성 */
    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
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
    public void deleteArticle(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new LogicException(TEMPORARY_ERROR));
        articleRepository.delete(foundedArticle);
    }

}
