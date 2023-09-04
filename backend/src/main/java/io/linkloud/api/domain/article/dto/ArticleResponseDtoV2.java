package io.linkloud.api.domain.article.dto;


import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
import lombok.Getter;

public class ArticleResponseDtoV2 {

    // 게시글 저장 responseDTO
    public record ArticleSave(Long id) {}

    // 특정 회원의 게시글 상태 response
    @Getter
    public static class MemberArticleStatusResponse {
        private final Long statusId;
        private final ReadStatus readStatus;
        private final Long memberId;
        private final Long articleId;

        public MemberArticleStatusResponse(MemberArticleStatus memberArticleStatus) {
            this.statusId = memberArticleStatus.getId();
            this.readStatus = memberArticleStatus.getReadStatus();
            this.memberId = memberArticleStatus.getMember().getId();
            this.articleId = memberArticleStatus.getArticle().getId();
        }
    }


}
