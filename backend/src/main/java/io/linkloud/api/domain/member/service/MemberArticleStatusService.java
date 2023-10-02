package io.linkloud.api.domain.member.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.*;

import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticleStatusResponse;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
import io.linkloud.api.domain.member.repository.MemberArticleStatusRepository;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberArticleStatusService {

    private final MemberArticleStatusRepository memberArticleStatusRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberArticleStatusResponse setArticleStatus(Long memberId, Long articleId, Long loginMemberId,
        ReadStatus readStatus) {
        validateMember(memberId, loginMemberId);
        Member member = findMemberById(memberId);
        Article article = findArticleById(articleId);

        MemberArticleStatus saveOrUpdate = saveOrUpdateArticleStatus(readStatus, member, article);

        MemberArticleStatus save = memberArticleStatusRepository.save(saveOrUpdate);
        log.info("Saved MemberArticleStatus - ID: {}, Article ID: {}, Member ID: {}, Read Status: {}",
            save.getId(), save.getArticle().getId(), save.getMember().getId(), save.getReadStatus().getStatus());
        return new MemberArticleStatusResponse(save);
    }

    /**
     *
     * @param readStatus 수정 혹은 저장될 게시글 상태
     * @param member     수정 요청한 회원
     * @param article    수정 요청한 해당 게시글
     * @return           수정 혹은 저장된 게시글 상태 객체
     */
    private MemberArticleStatus saveOrUpdateArticleStatus(ReadStatus readStatus, Member member,
        Article article) {
        return memberArticleStatusRepository.findByMemberAndArticle(
                member, article)
            .map(entity -> entity.updateStatus(readStatus))
            .orElseGet(() -> MemberArticleStatus.builder()
                .member(member)
                .article(article)
                .readStatus(readStatus)
                .build()
            );
    }

    /**
     * 회원의 게시글 상태 목록을 page size 범위 내에서 가져온다.
     * @param loginMemberId  로그인 된 회원 PK
     * @return               K : memberArticleStatus.getArticle.getId()
     *                       V : memberArticleStatus.readStatus()
     */
    @Transactional(readOnly = true)
    public Map<Long, ReadStatus>  findMemberArticlesByStatus(Long loginMemberId) {

        List<MemberArticleStatus> articleStatusByMemberId = memberArticleStatusRepository.findByMemberId(loginMemberId);


        int articleHasStatusSize = articleStatusByMemberId.size();

        log.info("{}번 회원의 게시글 상태 목록 총 {} 개 조회됨", loginMemberId, articleHasStatusSize);

        Map<Long, ReadStatus> articeStatusMap = new ConcurrentHashMap<>();

        for (MemberArticleStatus status : articleStatusByMemberId) {
            articeStatusMap.put(status.getArticle().getId(), status.getReadStatus());
        }

        log.info("{}번 회원 게시글 상태 조회 결과",loginMemberId);

        articeStatusMap.forEach((key,value) -> log.info("{}번 게시글 : {}",key,value));

        return articeStatusMap;
    }


    private Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new CustomException(ARTICLE_NOT_FOUND));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    public void validateMember(Long memberId, Long extractedMemberId) {
        if (!memberId.equals(extractedMemberId)) {
            throw new CustomException(LogicExceptionCode.MEMBER_NOT_MATCH);
        }
    }
}
