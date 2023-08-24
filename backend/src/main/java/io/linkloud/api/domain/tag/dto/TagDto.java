package io.linkloud.api.domain.tag.dto;

import io.linkloud.api.domain.tag.model.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class TagDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        Long id;
        String name;
        Long count;
    }

    public static class ArticleTagsResponse {
        private final Long id;
        private final String name;

        public ArticleTagsResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
