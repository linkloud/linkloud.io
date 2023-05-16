package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepositoryCustom {
    Boolean existsByName(String name);
    Optional<Tag> findTagByName(String name);
    Page<TagDto.Response> findAllOrderBy(Pageable pageable);
    List<TagDto.Response> findTagByNameIsStartingWith(String name);

}
