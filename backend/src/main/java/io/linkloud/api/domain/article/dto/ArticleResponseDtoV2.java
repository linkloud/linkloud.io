package io.linkloud.api.domain.article.dto;


import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.global.common.HasId;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ArticleResponseDtoV2 {

    // 게시글 저장 responseDTO
    public record ArticleSave(Long id) {}

    // 게시글 수정 responseDTO
    @Getter
    public static class ArticleUpdate {

        private Long id;

        private String title;

        private String url;

        private String description;

        private Integer views;

        private Integer hearts;

        private List<TagDto.ArticleTagsResponse> tags;

        private String ogImage;

        /**
         * Entity -> Dto
         */
        public ArticleUpdate(Article article) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.url = article.getUrl();
            this.description = article.getDescription();
            this.views = article.getViews();
            this.hearts = article.getHearts();
            this.tags = new ArrayList<>();
            if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
                article.getArticleTags().forEach(at -> this.tags.add(
                    new TagDto.ArticleTagsResponse(at.getTag().getId(), at.getTag().getName())));
            }
            this.ogImage = article.getOgImage();
        }
    }


    // 특정 회원의 게시글 상태 responseDTO
    @Getter
    public static class MemberArticleStatusResponse {
        private final ReadStatus readStatus;
        private final Long memberId;
        private final Long articleId;
        public MemberArticleStatusResponse(MemberArticleStatus memberArticleStatus) {
            this.readStatus = memberArticleStatus.getReadStatus();
            this.memberId = memberArticleStatus.getMember().getId();
            this.articleId = memberArticleStatus.getArticle().getId();
        }
    }


    // 내 게시글 최신순,인기순 정렬
    @Getter
    public static class MemberArticlesSortedResponse implements HasId {

        private final Long id;

        private final String title;

        private final String url;

        private final String description;

        private final Integer views;

        private final Integer hearts;

        private final List<TagDto.ArticleTagsResponse> tags;

        @Override
        public Long getId() {
            return this.id;
        }

        public MemberArticlesSortedResponse(Article article) {
            this.id = article.getId();
            this.title = article.getTitle();
            this.url = article.getUrl();
            this.description = article.getDescription();
            this.views = article.getViews();
            this.hearts = article.getHearts();
            this.tags = new ArrayList<>();
            if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
                article.getArticleTags().forEach(at -> this.tags.add(
                    new TagDto.ArticleTagsResponse(at.getTag().getId(), at.getTag().getName())));
            }
        }

        // 내가 설정한 다른 사람의 게시글 상태별 목록 조회
        @Getter
        public static class MemberArticlesByReadStatus implements HasId {

            private final Long id;
            private final String title;
            private final String url;
            private final String description;
            private final Integer views;
            private final Integer hearts;
            private final String readStatus;
            private final List<TagDto.ArticleTagsResponse> tags;

            @Override
            public Long getId() {
                return this.id;
            }

            public MemberArticlesByReadStatus(Article article, ReadStatus readStatus) {
                this.id = article.getId();
                this.title = article.getTitle();
                this.url = article.getUrl();
                this.description = article.getDescription();
                this.views = article.getViews();
                this.hearts = article.getHearts();
                this.readStatus = readStatus.name();
                this.tags = new ArrayList<>();
                if (article.getArticleTags() != null && !article.getArticleTags().isEmpty()) {
                    article.getArticleTags().forEach(at -> this.tags.add(
                        new TagDto.ArticleTagsResponse(at.getTag().getId(),
                            at.getTag().getName())));
                }
            }
        }
    }
}
