package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.tag.model.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Boolean existsByName(String name);
    Optional<Tag> findTagByName(String name);
    Page<Tag> findAll(Pageable pageable);
    List<Tag> findTagByNameIsStartingWith(String name);

}