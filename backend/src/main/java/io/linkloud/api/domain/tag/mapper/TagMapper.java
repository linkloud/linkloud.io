package io.linkloud.api.domain.tag.mapper;

import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public Tag postDtoToTag(TagDto.Post post) {
        return Tag.builder()
            .name(post.getName())
            .build();
    }
}
