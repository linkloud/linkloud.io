package io.linkloud.api.domain.tag.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ArticleTagDto {
    @Data
    @NoArgsConstructor
    public static class Post {
        private Long articleId;
        private List<String> tags;
    }
}
