package io.linkloud.api.domain.article.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_FOUND;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleSaveRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleSave;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleServiceV2Impl implements ArticleServiceV2{

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final TagService tagService;

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
