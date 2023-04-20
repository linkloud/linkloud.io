package io.linkloud.api.domain.tag.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class TagDto {
    @Data
    @NoArgsConstructor
    public static class Post {
        private String name;
    }

}
