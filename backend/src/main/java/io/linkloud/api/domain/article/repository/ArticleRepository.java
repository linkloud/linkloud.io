package io.linkloud.api.domain.article.repository;

import io.linkloud.api.domain.article.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
    /*
     * Containing : 검색 기능(LIKE)
     * IgnoreCase : 대소문자 구분 없음.
     * */

    // 조건 없이 모든 아티클 조회
    @Query(value = "SELECT DISTINCT a FROM Article a LEFT JOIN FETCH a.member LEFT JOIN FETCH a.articleTags t LEFT JOIN FETCH t.tag ORDER BY a.createdAt DESC",
        countQuery = "SELECT COUNT(DISTINCT a) FROM Article a")
    Page<Article> findAllArticle(Pageable pageable);

    // 제시된 키워드를 제목(title)으로 가지는 엔티티 조회
    @Query(value = "SELECT a FROM Article a JOIN FETCH a.member WHERE a.title LIKE %:keyword% ORDER BY a.createdAt DESC", countQuery = "select count(a) from Article a")
    Page<Article> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // 제시된 키워드를 내용(description)으로 가지는 엔티티 조회
    @Query(value = "SELECT a FROM Article a JOIN FETCH a.member WHERE a.description LIKE %:keyword% ORDER BY a.createdAt DESC", countQuery = "select count(a) from Article a")
    Page<Article> findByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);

    // 제시된 키워드를 제목(title) + 내용(description)으로 가지는 엔티티 조회
    // 'Join Fetch'로 참조할 멤버 테이블을 매핑.
    @Query(value = "SELECT a FROM Article a JOIN FETCH a.member WHERE a.title LIKE %:keyword% OR a.description LIKE %:keyword% ORDER BY a.createdAt DESC", countQuery = "select count(a) from Article a")
    Page<Article> findArticleByTitleOrDescription(String keyword, Pageable pageable);

}
