package io.linkloud.api.domain.article.repository;

import io.linkloud.api.domain.article.model.Article;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    /*
     * Containing : 검색 기능(LIKE)
     * IgnoreCase : 대소문자 구분 없음.
     * */

    // 조건 없이 모든 아티클 조회
    @Query(value = "SELECT DISTINCT a FROM Article a LEFT JOIN FETCH a.member LEFT JOIN FETCH a.articleTags t LEFT JOIN FETCH t.tag WHERE a.articleStatus = 'ACTIVE' ORDER BY a.createdAt DESC",
        countQuery = "SELECT COUNT(DISTINCT a) FROM Article a WHERE a.articleStatus = 'ACTIVE'")
    Page<Article> findAllArticle(Pageable pageable);

    @Query(value = "SELECT DISTINCT a FROM Article a LEFT JOIN FETCH a.member LEFT JOIN FETCH a.articleTags t LEFT JOIN FETCH t.tag WHERE a.id = :id AND a.articleStatus = 'ACTIVE' ORDER BY a.createdAt DESC")
    Optional<Article> findById(Long id);

    /**
     * 회원 ID 로 해당 회원이 작성한 게시글 ID 목록 조회
     * But page 요청 size 만큼만 조회
     * @param memberId 회원ID
     * @return 회원이 작성한 게시글
     */
    List<Article> findByMemberIdAndIdBetween(Long memberId,Long startArticleId,Long endArticleId);
}
