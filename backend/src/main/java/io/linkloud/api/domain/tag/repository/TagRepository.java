package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.tag.model.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Boolean existsByName(String name);
    List<Tag> findTagByNameIsStartingWith(String name);
}