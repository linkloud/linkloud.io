package io.linkloud.api.domain.openGraph.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class OpenGraphDto {

    private final String title;
    private final String image;
    private final String url;
    private final String description;


    @Builder
    public OpenGraphDto(String title, String image, String url, String description) {
        this.title = title;
        this.image = image;
        this.url = url;
        this.description = description;
    }
}
