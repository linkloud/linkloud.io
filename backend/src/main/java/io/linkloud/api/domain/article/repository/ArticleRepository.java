package io.linkloud.api.domain.article.repository;

import io.linkloud.api.domain.article.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 조건 없이 모든 아티클 조회
    Page<Article> findAll(Pageable pageable);

    /*
     * Containing : 검색 기능(LIKE)
     * IgnoreCase : 대소문자 구분 없음.
     * */

    // 제시된 키워드를 제목(title)으로 가지는 엔티티 조회
    Page<Article> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // 제시된 키워드를 내용(description)으로 가지는 엔티티 조회
    Page<Article> findByDescriptionContainingIgnoreCase(String keyword, Pageable pageable);

}
