package io.linkloud.api.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TagDto {
    @Data
    @NoArgsConstructor
    public static class Post {
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        Long id;
        String name;
        Long count;
    }

}
