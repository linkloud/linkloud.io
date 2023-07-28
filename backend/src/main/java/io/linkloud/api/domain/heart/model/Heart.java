package io.linkloud.api.domain.heart.model;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.global.audit.Auditable;
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

@Entity
@Table(name = "heart")
@NoArgsConstructor
@Getter
public class Heart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;


    @Builder
    public Heart(Member member, Article article) {
        this.member = member;
        this.article = article;
    }

}
