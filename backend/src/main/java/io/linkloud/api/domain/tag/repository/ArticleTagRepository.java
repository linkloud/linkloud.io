package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.tag.model.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

}
