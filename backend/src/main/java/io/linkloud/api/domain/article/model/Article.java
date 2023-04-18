package io.linkloud.api.domain.article.model;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.global.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "article")
@NoArgsConstructor
@Builder
@Getter
public class Article extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(nullable = false, length = 255)
    private String description;

    @ColumnDefault("0")
    private Integer views;

    @ColumnDefault("0")
    private Integer bookmarks;


    /** 생성자 */
    @Builder
    public Article(Long id, Member member, String title, String url, String description, Integer views, Integer bookmarks) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.url = url;
        this.description = description;
        this.views = views == null ? 0 : views; // 기본값 0으로 설정
        this.bookmarks = bookmarks == null ? 0 : bookmarks;
    }

    /** 아티클 수정 */
    public void articleUpdate(ArticleUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.url = updateDto.getUrl();
        this.description = updateDto.getDescription();
    }

    /** 조회수 변동 */
    public void articleViewIncrease(Integer views) {
        this.views = views;
    }

}
