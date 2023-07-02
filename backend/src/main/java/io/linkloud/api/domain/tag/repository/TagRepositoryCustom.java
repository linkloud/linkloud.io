package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.tag.dto.TagDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepositoryCustom {
    Page<TagDto.Response> findAllOrderBy(Pageable pageable);
    List<TagDto.Response> findTagByNameIsStartingWith(String name);

}
