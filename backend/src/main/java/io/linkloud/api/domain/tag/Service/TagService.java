package io.linkloud.api.domain.tag.Service;

import io.linkloud.api.domain.tag.repository.ArticleTagRepository;
import io.linkloud.api.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;

    public void addTag() {}

    public void addArticleTag() {}

    public void fetchTags() {}

    public void fetchTagListBySearch() {}


}
