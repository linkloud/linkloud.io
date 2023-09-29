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
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse.MemberArticlesByReadStatus;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
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
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
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

    // 게시글 한 개 조회
    // TODO : return V2 Dto
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

        Long id = articleRepository.save(article).getId();
        return new ArticleSave(id);
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


        // 로그인한 회원이라면
        // 해당 회원이 작성한 게시글 여부
        // 해당 회원이 설정한 게시글 상태 목록을 가져온다
        if (loginMemberId != null) {

            // 해당 회원이 작성한 게시글 여부
            flagMyArticle(loginMemberId, articlesWithNoOffset);

            long endArticleId = nextId - 1;
            long startArticleId = endArticleId - pageable.getPageSize() + 1;

            // 해당 회원이 설정한 게시글 상태 목록
            Map<Long, ReadStatus> memberArticlesByStatus = articleStatusService.findMemberArticlesByStatus(
                loginMemberId, startArticleId, endArticleId);

            for (ArticleListResponse listResponse : articlesWithNoOffset) {
                if (memberArticlesByStatus.containsKey(listResponse.getId())) {
                    listResponse.setReadStatus(memberArticlesByStatus.get(listResponse.getId()));
                }
            }
        }


        return articlesWithNoOffset;
    }




    // 내 게시글 목록 최신순,인기순 정렬 조회
    @Transactional(readOnly = true)
    public Slice<MemberArticlesSortedResponse> findArticlesByMemberSorted(Long loginMemberId,Long memberId,Long lastArticleId,Pageable pageable,
        SortBy sortBy) {
        memberService.validateMember(memberId, loginMemberId);
        return articleRepository.findArticlesByMemberSorted(memberId, lastArticleId, pageable, sortBy);
    }

    // 게시글 목록 상태(읽음,읽는중)순 정렬
    @Transactional(readOnly = true)
    public Slice<MemberArticlesByReadStatus> findArticlesByReadStatus(Long loginMemberId,Long memberId,Long lastArticleId,Pageable pageable,
        ReadStatus readStatus) {
        memberService.validateMember(memberId, loginMemberId);
        return articleRepository.findArticlesByReadStatus(memberId, lastArticleId, pageable,readStatus);
    }

    // 게시글 검색
    @Transactional(readOnly = true)
    @Override
    public Slice<ArticleListResponse> searchArticleByKeywordOrTags(Long loginMemberId, String keyword,
        List<String> tags, Pageable pageable) {

        validateSearch(keyword, tags);

        Slice<ArticleListResponse> articlesByKeywordOrTags = articleRepository.findArticlesByKeywordOrTags(
            keyword, tags, pageable);


        flagMyArticle(loginMemberId, articlesByKeywordOrTags);


        return articlesByKeywordOrTags;
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

    // 내 게시글이 존재하면 author = true
    private void flagMyArticle(@NonNull Long loginMemberId,
        Slice<ArticleListResponse> articlesWithNoOffset) {

        articlesWithNoOffset.getContent().stream()
            .filter(articleDto -> articleDto.getMemberId().equals(loginMemberId))
            .forEach(articleDto -> articleDto.setAuthor(true));
        /** Stream 으로 변경
         if (loginMemberId != null) {
         for (ArticleListResponse articleDto : articlesWithNoOffset) {
         if (articleDto.getMemberId().equals(loginMemberId)) {
         articleDto.setAuthor(true);
         }
         }
         }
         **/
    }

}
