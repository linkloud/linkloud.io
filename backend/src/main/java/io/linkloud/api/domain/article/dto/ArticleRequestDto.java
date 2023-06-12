package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 05-28 Long memberId 삭제 (@LoginMemberID 어노테이션으로 memberId 를 받아옴)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ArticleRequestDto {

    @NotBlank(message = "제목은 빈칸으로 둘 수 없습니다.")
    @Size(min = 1, max = 50, message = "제목은 한 글자 이상, 50자 이하로 작성해야 합니다.")
    private String title;

    @NotBlank(message = "링크는 빈칸으로 둘 수 없습니다.")
    @Size(max = 255, message = "링크의 길이가 너무 깁니다.")
    private String url;

    @NotBlank(message = "본문은 빈칸으로 둘 수 없습니다.")
    @Size(max = 255, message = "본문은 255자 이하로 작성해야 합니다.")
    private String description;

    @Size(max = 5)
    private List<
        @Pattern(regexp = "^[0-9A-Za-z가-힣-]*$")
        @NotBlank
        @Length(min=2, max=20)
            String
        > tags;

    /** Dto -> Entity */
    public Article toArticleEntity(Member member) {

        Article article = Article.builder()
            .member(member)
            .title(title)
            .url(url)
            .description(description)
            .build();

        return article;
    }

}
