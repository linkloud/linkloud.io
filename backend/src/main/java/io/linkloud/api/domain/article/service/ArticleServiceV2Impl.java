package io.linkloud.api.domain.article.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.ARTICLE_NOT_FOUND;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.BAD_REQUEST;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_FOUND;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_MATCH;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleUpdateRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleListResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleUpdate;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesByCondition;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.heart.service.HeartService;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberArticleStatusService;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.service.TagService;
import io.linkloud.api.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleServiceV2Impl implements ArticleServiceV2{

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final TagService tagService;
    private final MemberService memberService;
    private final MemberArticleStatusService articleStatusService;
    private final HeartService heartService;

    // 게시글 한 개 조회
    @Transactional
    @Override
    public ArticleResponseDto getArticleById(Long id) {
        Article getArticle = findArticleById(id);
        getArticle.increaseViewCount();  // 조회수 증가

        return new ArticleResponseDto(getArticle);
    }

    // 게시글 생성
    @Transactional
    @Override
    public ArticleSave addArticle(ArticleSaveRequestDto articleSaveRequestDto,Long memberId) {
        Member member = findMemberById(memberId);

        // 태그 조회 및 저장
        List<ArticleTag> articleTags = new ArrayList<>();
        if (articleSaveRequestDto.tags() != null && !articleSaveRequestDto.tags().isEmpty()) {
            articleTags = addArticleTagList(articleSaveRequestDto.tags());
        }
        Article article = articleSaveRequestDto.toEntity(member);
        // 연관 관계 추가
        article.addArticleTag(articleTags);

        Long articleId = articleRepository.save(article).getId();
        articleStatusService.initArticleAsUnread(member,article);
        return new ArticleSave(articleId);
    }


    @Override
    @Transactional
    public ArticleUpdate updateArticle(ArticleUpdateRequestDto updateDto,
        Long articleId, Long loginMemberId) {

        Member member = findMemberById(loginMemberId);
        Article article = findArticleById(articleId);


        // 요청한 회원ID, 수정하려는 게시글의 회원ID 비교
        validateMemberArticleMatch(member, article);

        article.articleUpdate(updateDto);

        if (updateDto.getTags() != null && !updateDto.getTags().isEmpty()) {
            tagService.tagFilterAndSave(updateDto, article);
        }
        return new ArticleUpdate(article);
    }


    // 게시글 삭제
    @Transactional
    @Override
    public void deleteArticle(Long loginMemberId, Long articleId) {
        Member member = findMemberById(loginMemberId);
        Article article = findArticleById(articleId);
        validateMemberArticleMatch(member, article);

        article.deleteArticle();
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    @Override
    public Slice<ArticleListResponse> findArticlesWithNoOffset(Long nextId,Long loginMemberId, Pageable pageable,
        SortBy sortBy) {


        Slice<ArticleListResponse> articlesWithNoOffset = articleRepository.findArticlesWithNoOffset(
            nextId, pageable, sortBy);


        if (loginMemberId != null) {
            flagArticlesForLoggedInMember(loginMemberId, articlesWithNoOffset);
        }


        return articlesWithNoOffset;
    }


    // 내 게시글 목록 최신순,인기순 정렬 조회 OR 게시글 상태로 조회
    @Transactional(readOnly = true)
    @Override
    public Slice<MemberArticlesByCondition> MemberArticlesByCondition(Long loginMemberId, Long memberId,
        Long lastArticleId, Pageable pageable,String sortBy) {

        memberService.validateMember(memberId, loginMemberId);
        ReadStatus readStatus = ReadStatus.fromString(sortBy);
        SortBy titleOrLatest = SortBy.fromString(sortBy);

        List<Long> myArticleIds = findMyArticleIds(loginMemberId);

        Slice<MemberArticlesByCondition> memberArticlesByConditions = articleRepository.MemberArticlesByCondition(
            memberId, titleOrLatest, readStatus, lastArticleId, pageable);
        log.info("memberId={} created articles size={}",loginMemberId,myArticleIds.size());
        // 내 북마크(최신순,상태순) 게시글들중에
        // 내가 작성한 게시글 즉 dto.getId() == myArticle.getId() 이면
        // 내가 작성한 글이므로 isAuthor == true
        for (MemberArticlesByCondition dto : memberArticlesByConditions) {
            if (myArticleIds.contains(dto.getId())) {
                dto.setAuthor();
            }
        }

        return memberArticlesByConditions;
    }


    // 게시글 검색
    @Transactional(readOnly = true)
    @Override
    public Slice<ArticleListResponse> searchArticleByKeywordOrTags(Long loginMemberId, String keyword,
        List<String> tags, Pageable pageable) {

        validateSearch(keyword, tags);

        Slice<ArticleListResponse> articlesByKeywordOrTags = articleRepository.findArticlesByKeywordOrTags(
            keyword, tags, pageable);

        if (loginMemberId != null && !articlesByKeywordOrTags.isEmpty()) {
            flagArticlesForLoggedInMember(loginMemberId,articlesByKeywordOrTags);
        }

        return articlesByKeywordOrTags;
    }

    /**
     *  로그인을 한 회원이 게시글 목록 조회 시
     * 1. 로그인 한 회원이면 작성자 여부
     * 2. 게시글 상태
     * 3. 게시글 좋아요 여부를 조회한다.
     *
     * @param loginMemberId 로그인 회원 PK
     * @param articles      로그인 회원의 게시글 목록
     */
    private void flagArticlesForLoggedInMember(Long loginMemberId, Slice<ArticleListResponse> articles){

        flagAuthorForArticles(articles, loginMemberId);
        flagReadStatusForArticles(articles, loginMemberId);
        flagLikedStatusForArticles(articles, loginMemberId);
    }


    private void validateSearch(String keyword, List<String> tags) {
        if ((keyword == null || keyword.isEmpty()) && (tags == null || tags.isEmpty())) {
            throw new CustomException(BAD_REQUEST);
        }
        if (tags != null && tags.size() > 5) {
            throw new CustomException(BAD_REQUEST);
        }
    }

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

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    private Article findArticleById(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
    }

    private void validateMemberArticleMatch(Member member, Article article) {
        if (!article.getMember().getId().equals(member.getId())) {
            throw new CustomException(MEMBER_NOT_MATCH);
        }
    }

    // memberId 로 요청 size 만큼 해당 회원이 작성한 글 조회



    // 게시글 작성자 설정
    private void flagAuthorForArticles(Slice<ArticleListResponse> articles, Long loginMemberId) {

        List<Long> myArticleIds = findMyArticleIds(loginMemberId);
        log.info("{}번 회원이 작성한 총 게시글 수 : {}", loginMemberId, myArticleIds.size());
        for (ArticleListResponse listResponse : articles) {
            if (myArticleIds.contains(listResponse.getId())) {
                log.info("{}번 회원 {}번 게시글 작성자 설정 완료",loginMemberId,listResponse.getId());
                listResponse.setAuthor(true);
            }
        }
    }

    private List<Long> findMyArticleIds(Long loginMemberId) {
        return articleRepository.findByMemberId(loginMemberId)
            .stream()
            .map(Article::getId)
            .toList();
    }

    // 게시글 상태 설정
    private void flagReadStatusForArticles(Slice<ArticleListResponse> articles, Long loginMemberId) {
        Map<Long, ReadStatus> memberArticlesByStatus = articleStatusService.findMemberArticlesByStatus(loginMemberId);
        for (ArticleListResponse listResponse : articles) {
            if (memberArticlesByStatus.containsKey(listResponse.getId())) {
                listResponse.setReadStatus(memberArticlesByStatus.get(listResponse.getId()));
            }
        }
    }

    // 게시글 좋아요 여부
    private void flagLikedStatusForArticles(Slice<ArticleListResponse> articles, Long loginMemberId) {
        List<Long> heartedArticleIds = heartService.findHeartedArticleIdsByMemberId(loginMemberId);
        for (ArticleListResponse listResponse : articles) {
            if (heartedArticleIds.contains(listResponse.getId())) {
                listResponse.setLiked(true);
            }
        }
    }

}
