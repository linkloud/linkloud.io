package io.linkloud.api.domain.member.model;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.ReadStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_article_status")
public class MemberArticleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "read_status")
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    @Builder
    public MemberArticleStatus(Member member, Article article, ReadStatus readStatus) {
        this.member = member;
        this.article = article;
        this.readStatus = readStatus;
    }

    public MemberArticleStatus updateStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
        return this;
    }
}
