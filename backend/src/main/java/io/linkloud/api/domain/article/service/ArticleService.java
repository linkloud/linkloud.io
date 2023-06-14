package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.*;

@Service
@RequiredArgsConstructor
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
        Article foundedArticle = fetchArticleEntityById(id);
        foundedArticle.articleViewIncrease(foundedArticle.getViews() + 1);  // 조회수 증가

        return new ArticleResponseDto(foundedArticle);
    }

    /**
     * 게시글 생성
     * 05-28 Long memberId 파라매터 추가됨
     * @param memberId principal's member Id
     * @param requestDto 게시글 내용 요청 dto
     * @return 게시글 정보
     */
    @Transactional
    public ArticleResponseDto addArticle(Long memberId, ArticleRequestDto requestDto) {
        Member member = fetchMemberById(memberId);

        Article createdArticle = articleRepository.save(requestDto.toArticleEntity(member));            // requestDto를 엔티티로 변환.

        return new ArticleResponseDto(createdArticle);
    }

    /**
     * 게시글 수정
     * 05-28 Long memberId 파라미터 추가됨
     * @param articleId 수정하려는 게시글 ID
     * @param memberId  principal 회원 ID
     * @param updateDto 수정내용 DTO
     * @return 수정된 게시글 DTO
     */
    @Transactional
    public ArticleResponseDto updateArticle(Long articleId, Long memberId, ArticleUpdateDto updateDto) {
        Member member = fetchMemberById(memberId);
        Article article = fetchArticleEntityById(articleId);

        // 요청한 회원ID, 수정하려는 게시글의 회원ID 비교
        validateMemberArticleMatch(member, article);
        article.articleUpdate(updateDto);

        return new ArticleResponseDto(article);
    }

    /**
     * 게시글 삭제
     * 05-28 Long memberId 파라미터 추가됨
     * @param articleId 삭제하려는 게시글 ID
     * @param memberId  principal 회원 ID
     */
    @Transactional
    public void removeArticle(Long articleId, Long memberId) {
        Member member = fetchMemberById(memberId);
        Article article = fetchArticleEntityById(articleId);

        // 요청한 회원ID, 삭제하려는 게시글의 회원ID 비교
        validateMemberArticleMatch(member, article);
        articleRepository.delete(article);
    }

    /**
     * articleId 로 article 찾기
     * @param id articleId
     * @return ArticleEntity
     */
    private Article fetchArticleEntityById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
    }

    /**
     * memberId 로 member 찾기
     * service 에서 service 호출보다 repository 를 호출하는게 더 빠름
     * @param memberId principal's memberId
     * @return Member
     */
    private Member fetchMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    /**
     * principal memberId 와 수정하려는 게시글을 작성한 memberId 가 같은지 비교
     * @param member principal's memberId
     * @param article 수정하려는 게시글
     */
    private void validateMemberArticleMatch(Member member, Article article) {
        if (!article.getMember().getId().equals(member.getId())) {
            throw new CustomException(MEMBER_NOT_MATCH);
        }
    }

    /** 검색 */
    @Transactional
    public Page<ArticleResponseDto> fetchArticleBySearch(String keyword, String keywordType, int page) {
        /* 키워드 목록
        * title               : 제목
        * description         : 내용
        * titleAndDescription : 제목 + 내용
        */
        Page<Article> articlesPage;
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("createdAt").descending());

        // 제목으로 검색
        if(keywordType.equals("title")){
            articlesPage = articleRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }
        // 글 내용으로 검색
        else if (keywordType.equals("description")) {
            articlesPage = articleRepository.findByDescriptionContainingIgnoreCase(keyword, pageable);
        }
        // 제목 + 글 내용으로 검색
        else if (keywordType.equals("titleAndDescription")) {
            // 제목 검색결과, 내용 검색 결과 저장
            Page<Article> page1 = articleRepository.findByTitleContainingIgnoreCase(keyword, pageable);
            Page<Article> page2 = articleRepository.findByDescriptionContainingIgnoreCase(keyword, pageable);

            // 결과 합치기
            List<Article> combinedContent = new ArrayList<>();
            combinedContent.addAll(page1.getContent());
            combinedContent.addAll(page2.getContent());

            // 중복 제거 및 정렬
            List<Article> distinctArticles = combinedContent.stream()
                .distinct()
                .sorted(Comparator.comparing(Article::getCreatedAt).reversed())
                .collect(Collectors.toList());

            articlesPage = new PageImpl(distinctArticles, pageable, distinctArticles.size());
        }
        // 검색 범주 미 설정시 예외 처리.
        else {
            throw new CustomException(BAD_REQUEST);  //TODO: 예외 처리 코드 정하기
        }

        return articlesPage.map(article -> new ArticleResponseDto(article));
    }

}
