package io.linkloud.api.domain.member.repository;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberArticleStatusRepository extends JpaRepository<MemberArticleStatus,Long> {

    Optional<MemberArticleStatus> findByMemberAndArticle(Member member, Article article);

    List<MemberArticleStatus> findByMemberId(Long memberId);
}
