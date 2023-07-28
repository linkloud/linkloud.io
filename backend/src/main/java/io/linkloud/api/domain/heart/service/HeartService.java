package io.linkloud.api.domain.heart.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.ARTICLE_NOT_FOUND;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_FOUND;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.heart.model.Heart;
import io.linkloud.api.domain.heart.repository.HeartRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;

    /** 아티클ID로 아티클 찾기 */
    private Article fetchArticleById(Long id) {
        return articleRepository.findById(id)
            .orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
    }

    /** 멤버ID로 멤버 찾기 */
    private Member fetchMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    /** 좋아요한 아티클ID로 좋아요한 아티클 찾기 */
    @Transactional
    public Heart fetchHeartById(Long id) {
        return heartRepository.findById(id)
            .orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));  // 임시로 지정
    }

}
