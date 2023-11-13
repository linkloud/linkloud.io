package io.linkloud.api.domain.tag.model;

import io.linkloud.api.domain.article.model.Article;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table
@Getter
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    @Builder
    public ArticleTag(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }

    // Article 객체와의 연관 관계를 제거하는 메소드
    public void setArticle(Article article) {
        this.article = article;
    }
    // 연관 관계 매핑
    public void addArticle(Article article) {
        this.article = article;
    }
}