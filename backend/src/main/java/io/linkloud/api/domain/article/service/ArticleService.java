package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
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
    public ArticleResponseDto addArticle(Long memberId,ArticleRequestDto requestDto) {
        Member foundedMember = fetchMemberById(memberId);

        LocalDate joinDate = foundedMember.getCreatedAt().toLocalDate();                             // 가져온 멤버의 가입일을 저장.
        if(joinDate.compareTo(LocalDate.now()) >-3) throw new CustomException(MEMBER_NOT_MATCH);    // 가입일을 오늘과 비교했을때 -3보다 크다면(3일이 지나지 않았다면), 403(권한)에러.

        Article createdArticle = articleRepository.save(requestDto.toArticleEntity(foundedMember));  // requestDto를 엔티티로 변환.

        return new ArticleResponseDto(createdArticle);
    }

    /**
     * 게시글 수정
     * 05-28 Long memberId 파라매터 추가됨
     * @param articleId 게시글 ID
     * @param memberId  작성자 ID
     * @param updateDto 수정내용 DTO
     * @return 수정된 게시글 DTO
     */
    @Transactional
    public ArticleResponseDto updateArticle(Long articleId,Long memberId,ArticleUpdateDto updateDto) {
        Member member = fetchMemberById(memberId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));   // 수정할 아티클 조회

        // 요청한 회원, 수정하려는 게시글의 회원 비교
        validateMemberArticleMatch(member, article);
        article.articleUpdate(updateDto);

        return new ArticleResponseDto(article);
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


    /** 아티클 삭제 */
    @Transactional
    public void removeArticle(Long id) {
        Article foundedArticle = articleRepository.findById(id).orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
        articleRepository.delete(foundedArticle);
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

}
