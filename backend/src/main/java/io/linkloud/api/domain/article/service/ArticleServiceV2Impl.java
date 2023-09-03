package io.linkloud.api.domain.article.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_FOUND;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.service.TagService;
import io.linkloud.api.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    @Override
    public ArticleSave addArticle(ArticleSaveRequestDto articleSaveRequestDto,Long memberId) {
        Member member = fetchMemberById(memberId);

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

    @Transactional(readOnly = true)
    @Override
    public Slice<ArticleResponseDto> findArticlesWithNoOffset(Long lastArticleId, Pageable pageable,
        SortBy sortBy) {
        return articleRepository.findArticlesWithNoOffset(lastArticleId, pageable,sortBy);
    }

    @Transactional(readOnly = true)
    public Slice<ArticleResponseDto> findMyArticles(Long loginMemberId,Long memberId,Long lastArticleId,Pageable pageable,
        SortBy sortBy) {
        memberService.validateMember(memberId, loginMemberId);
        return articleRepository.findMyArticlesWithNoOffset(memberId,lastArticleId, pageable,sortBy);
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

    private Member fetchMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}
