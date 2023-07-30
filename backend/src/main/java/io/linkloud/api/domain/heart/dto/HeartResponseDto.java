package io.linkloud.api.domain.heart.dto;

import io.linkloud.api.domain.heart.model.Heart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HeartResponseDto {

    private Long id;

    private Long memberId;

    private Long articleId;

    /** Entity -> Dto */
    public HeartResponseDto(Heart heart) {
        this.id = heart.getId();
        this.memberId = heart.getMember().getId();
        this.articleId = heart.getArticle().getId();
    }

}
