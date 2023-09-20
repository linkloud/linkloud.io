package io.linkloud.api.domain.article.model;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleUpdateRequestDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.dto.ArticleStatusRequest;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.global.audit.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "article")
@NoArgsConstructor
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
    private Integer hearts;

    @OneToMany(mappedBy = "article", cascade = CascadeType.PERSIST)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus articleStatus = ArticleStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus = ReadStatus.UNREAD;

    @Column(nullable = true)
    private String ogImage;
    @AllArgsConstructor
    public enum SortBy {
        LATEST("createdAt"),
        TITLE("title"),
        HEARTS("hearts"),
        READING("reading"),
        READ("read");

        @Getter
        private String sortBy;
    }

    /** 생성자 */
    @Builder
    public Article(Long id, Member member, String title, String url, String description, Integer views, Integer hearts,String ogImage) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.url = url;
        this.description = description;
        this.ogImage = ogImage;
        this.views = views == null ? 0 : views; // 기본값 0으로 설정
        this.hearts = hearts == null ? 0 : hearts;
    }

    /** 아티클 수정 */
    public void articleUpdate(ArticleUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.url = updateDto.getUrl();
        this.description = updateDto.getDescription();
    }

    /** 아티클 수정 V2*/
    public void articleUpdate(ArticleUpdateRequestDto updateDto) {
        this.title = updateDto.getTitle();
        this.url = updateDto.getUrl();
        this.description = updateDto.getDescription();
    }

    /** 조회수 변동 */
    public void articleViewIncrease(Integer views) {
        this.views = views;
    }

    public void increaseViewCount() {
        this.views += 1;
    }

    /** 좋아요 변동 */
    public void articleHeartChange(Integer hearts) {
        this.hearts = hearts;
    }

    // 연관 관계 양방향 매핑
    public void addArticleTag(List<ArticleTag> articleTags) {
        if (articleTags != null && articleTags.size() != 0) {
            for (ArticleTag articleTag : articleTags) {
                articleTag.addArticle(this);
            }
        }
        this.articleTags = articleTags;
    }

    public void updateArticleStatus(ArticleStatusRequest articleStatusRequest) {
        this.readStatus = articleStatusRequest.getReadStatus();
    }

    public void deleteArticle() {
        this.articleStatus = ArticleStatus.INACTIVE;
    }
}
