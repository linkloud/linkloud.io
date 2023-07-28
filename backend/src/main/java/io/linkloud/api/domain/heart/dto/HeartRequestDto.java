package io.linkloud.api.domain.heart.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.heart.model.Heart;
import io.linkloud.api.domain.member.model.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HeartRequestDto {

    /** Dto -> Entity */
    public Heart toLikeEntity(Member member, Article article) {
        Heart heart = Heart.builder()
            .member(member)
            .article(article)
            .build();

        return heart;
    }

}
