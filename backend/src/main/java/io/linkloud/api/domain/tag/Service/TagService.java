package io.linkloud.api.domain.tag.Service;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.tag.dto.ArticleTagDto;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.repository.ArticleTagRepository;
import io.linkloud.api.domain.tag.repository.TagRepository;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleRepository articleRepository;

    public void addTag(Tag tag) {
        verifyExistTag(tag.getName());
        tagRepository.save(tag);

        log.info("Tag 생성 완료.");
    }

    // 임시용임
    // TODO : 아티클 기능 완성시 아티클 생성로직으로 편입될 예정.
    public void addArticleTag(ArticleTagDto.Post post) {
        // 태그들이 비어있을 경우 예외 발생.
        if(post.getTags().isEmpty()) {
            throw new LogicException(ExceptionCode.JSON_REQUEST_FAILED);
        }

        Optional<Article> optionalArticle = articleRepository.findById(post.getArticleId());

        Article foundArticle = optionalArticle
            .orElseThrow(() -> new LogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<Tag> tags = new ArrayList<>();
        List<ArticleTag> articleTags = new ArrayList<>();

        // dto에서 태그를 확인 해 리스트에 추가.
        post.getTags().forEach(t -> tags.add(fetchTagByName(t)));

        // 연관 엔티티 생성해서 리스트에 추가 후 전부 저장.
        tags.forEach(t -> articleTags.add(new ArticleTag(foundArticle, t)));
        articleTagRepository.saveAll(articleTags);

        log.info("아티클에 태그 추가.");
    }

    public Page<Tag> fetchTags(int page, String sort) {

        // 이름순은 오름차순.
        Sort.Direction sd = Direction.DESC;
        if(sort.equals("name")) {
            sd = Direction.ASC;
        }

        PageRequest pageable = PageRequest.of(page - 1, 20, Sort.by(sd, sort));
        return tagRepository.findAll(pageable);
    }

    public void fetchTagListBySearch() {}

    private void verifyExistTag(String tagName) {
        boolean verifiedTag = tagRepository.existsByName(tagName);
        if(verifiedTag) {
            // 임시용 이기 때문에 409 아무거나
            // TODO : 예외 코드 추가하거나, 태그 생성 기능을 삭제하면 같이 삭제할 예정.
            throw new LogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
        }
    }

    // 이름으로 태그 가져오기
    private Tag fetchTagByName(String name){
        Optional<Tag> optionalTag = tagRepository.findTagByName(name);

        return optionalTag
            .orElseThrow(() -> new LogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
