package io.linkloud.api.domain.article.dto;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.hibernate.validator.constraints.Length;

public class ArticleRequestDtoV2 {

    public record ArticleSaveRequestDto(
        @NotBlank(message = "제목은 빈칸으로 둘 수 없습니다.")
        @Size(min = 1, max = 50, message = "제목은 한 글자 이상, 50자 이하로 작성해야 합니다.")
        String title,
        @NotBlank(message = "링크는 빈칸으로 둘 수 없습니다.")
        @Size(max = 255, message = "링크의 길이가 너무 깁니다.")
        String url,

        @Size(max = 255, message = "본문은 255자 이하로 작성해야 합니다.")
        String description,

        @Size(max = 5)
        List<@Pattern(regexp = "^[0-9A-Za-z가-힣-]*$")
            @NotBlank
            @Length(min = 2, max = 20)
            String> tags){
        public Article toEntity(Member member) {
            return Article.builder()
                .member(member)
                .title(title)
                .url(url)
                .description(description)
                .build();
        }
    }
}
