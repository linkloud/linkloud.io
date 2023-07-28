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

    private Long member_id;

    private Long article_id;

    /** Entity -> Dto */
    public HeartResponseDto(Heart heart) {
        this.id = heart.getId();
        this.member_id = heart.getMember().getId();
        this.article_id = heart.getArticle().getId();
    }

}
