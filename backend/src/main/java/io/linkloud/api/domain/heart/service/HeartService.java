package io.linkloud.api.domain.heart.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.ARTICLE_NOT_FOUND;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.HEART_ALREADY_EXISTS;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.HEART_NOT_FOUND;
import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_FOUND;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.heart.dto.HeartRequestDto;
import io.linkloud.api.domain.heart.dto.HeartResponseDto;
import io.linkloud.api.domain.heart.model.Heart;
import io.linkloud.api.domain.heart.repository.HeartRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeartService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;



    /** 좋아요ID로 좋아요 찾기 */
    @Transactional
    public Heart fetchHeartById(Long id) {
        return heartRepository.findById(id)
            .orElseThrow(() -> new CustomException(HEART_NOT_FOUND));  // 좋아요를 하지 않아서 없을 경우 404에러
    }

    /** 좋아요 생성 */
    @Transactional
    public HeartResponseDto addHeart(Long memberId, Long articleId, HeartRequestDto heartRequestDto) {
        Member member = fetchMemberById(memberId);
        Article article = fetchArticleById(articleId);

        // 이미 좋아요한 게시글인지 검증
        if (heartRepository.findByMemberAndArticle(member, article).isPresent()) {
            throw new CustomException(HEART_ALREADY_EXISTS);
        }

        Heart createdHeart = heartRequestDto.toHeartEntity(member, article);
        heartRepository.save(createdHeart);
        article.articleHeartChange(article.getHearts() + 1);

        return new HeartResponseDto(createdHeart);
    }

    /** 좋아요 삭제 */
    @Transactional
    public void removeHeart(Long memberId, Long articleId) {
        Member member = fetchMemberById(memberId);
        Article article = fetchArticleById(articleId);

        // 좋아요를 누른 적이 없다면 예외로 처리
        Heart deletedHeart = heartRepository.findByMemberAndArticle(member, article)
            .orElseThrow(() -> new CustomException(HEART_NOT_FOUND));

        heartRepository.delete(deletedHeart);
        article.articleHeartChange(article.getHearts() - 1);
    }

    /**
     * 회원 ID 로 해당 회원이 좋아요를 누른 게시글의 ID 목록 반환
     *
     * @param memberId 조회하고자 하는 회원의 ID
     * @return 해당 회원이 좋아요를 누른 게시글의 ID 목록. 만약 좋아요를 누른 게시글이 없다면 빈 리스트를 반환
     *
     */
    @Transactional(readOnly = true)
    public List<Long> findHeartedArticleIdsByMemberId(Long memberId) {
        List<Heart> hearts = heartRepository.findByMemberId(memberId);
        log.info("{}번 회원은 총 {} 개의 좋아요한 게시글이 있습니다.", memberId, hearts.size());

        List<Long> likedArticleIds = new ArrayList<>();
        for (Heart heart : hearts) {
            likedArticleIds.add(heart.getId());
        }
        return likedArticleIds;
    }

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

}
