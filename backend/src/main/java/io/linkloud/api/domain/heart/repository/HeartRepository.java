package io.linkloud.api.domain.heart.repository;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.heart.model.Heart;
import io.linkloud.api.domain.member.model.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberAndArticle(Member member, Article article);

    List<Heart> findByMemberId(Long memberId);

}
