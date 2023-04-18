package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleRequestDto {

    @NotNull
    private Member member_id;

    @NotBlank(message = "제목은 빈칸으로 둘 수 없습니다.")
    @Size(min = 1, max = 50, message = "제목은 한 글자 이상, 50자 이하로 작성해야 합니다.")
    private String title;

    @NotBlank(message = "링크는 빈칸으로 둘 수 없습니다.")
    @Size(max = 255, message = "링크의 길이가 너무 깁니다.")
    private String url;

    @NotBlank(message = "본문은 빈칸으로 둘 수 없습니다.")
    @Size(max = 255, message = "본문은 255자 이하로 작성해야 합니다.")
    private String description;


    /** Dto -> Entity */
    public Article toAriticleEntity() {
        Article article = Article.builder()
            .member(member_id)
            .title(title)
            .url(url)
            .description(description)
            .build();

        return article;
    }

}
