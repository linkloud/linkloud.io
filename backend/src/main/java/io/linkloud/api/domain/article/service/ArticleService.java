package io.linkloud.api.domain.article.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.service.TagService;
import io.linkloud.api.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final TagService tagService;


    /** 아티클 모두 반환 */
    @Transactional(readOnly = true)
    public Page<ArticleResponseDto> fetchAllArticle(int page) {
        Page<Article> articlePage = articleRepository.findAllArticle(PageRequest.of(page - 1, 10, Sort.by("createdAt").descending()));

        return articlePage.map(article -> new ArticleResponseDto(article));
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

        // 태그 조회 및 저장
        List<ArticleTag> articleTags = new ArrayList<>();
        if (requestDto.getTags() != null && !requestDto.getTags().isEmpty()) {
            articleTags = addArticleTagList(requestDto.getTags());
        }

        Article article = requestDto.toArticleEntity(member);
        // 연관 관계 추가
        article.addArticleTag(articleTags);

        Article createdArticle = articleRepository.save(article);

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

        List<ArticleTag> articleTags = new ArrayList<>();
        if (updateDto.getTags() != null && !updateDto.getTags().isEmpty()) {
            articleTags = addArticleTagList(updateDto.getTags());
        }
        article.getArticleTags().clear();
        article.addArticleTag(articleTags);

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
        article.deleteArticle();
    }

    // 태그 연관 관계 추가
    private List<ArticleTag> addArticleTagList(List<String> tagNames) {
        List<Tag> tags = addTags(tagNames);
        // ArticleTag 설정
        List<ArticleTag> articleTags = new ArrayList<>();

        for (Tag tag : tags) {
            articleTags.add(new ArticleTag(null, tag));
        }

        return articleTags;
    }

    // 태그들 저장
    private List<Tag> addTags(List<String> tagNames) {
        return tagService.addTags(tagNames);
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
    public Page<ArticleResponseDto> fetchArticleBySearch(String keyword, List<String> tags, int page) {
        /* 키워드 목록
        * title               : 제목
        * description         : 내용
        * titleAndDescription : 제목 + 내용
        */
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("createdAt").descending());


        return articleRepository.findArticleListBySearch(keyword, tags, pageable);
    }

}
