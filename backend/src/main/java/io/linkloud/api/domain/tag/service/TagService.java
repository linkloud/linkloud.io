package io.linkloud.api.domain.tag.service;

import io.linkloud.api.domain.article.dto.ArticleRequestDtoV2.ArticleUpdateRequestDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.repository.TagRepository;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    // 태그들 저장
    public List<Tag> addTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = addtag(tagName);
            tags.add(tag);
        }

        return tags;
    }

    // TODO : 중복 태그 안걸러지고 그냥 저장됨
    // 중복 Tag 조회 후 저장.
    private Tag addtag(String tagName) {
        // 동일한 태그가 존재하면 바로 반환.
        Optional<Tag> findTag = tagRepository.findTagByName(tagName);
        if (findTag.isPresent()) {
            return findTag.get();
        }

        Tag tag = Tag.builder()
                .name(tagName)
                .build();

        Tag adddTag = tagRepository.save(tag);
        log.info("Tag 생성 완료. {}", adddTag.getName());
        return adddTag;
    }

    public Page<TagDto.Response> fetchTags(int page, int size, String sortField) {

        // 이름순은 오름차순.
        Sort.Direction orderBy = Direction.DESC;
        if (sortField.equals("name")) {
            orderBy = Direction.ASC;
        }

        PageRequest pageable = PageRequest.of(page - 1, size, Sort.by(orderBy, sortField));
        return tagRepository.findAllOrderBy(pageable);
    }

    public List<TagDto.Response> fetchTagListBySearch(String keyword) {
        return tagRepository.findTagByNameIsStartingWith(keyword);
    }



    /**
     * 중복 태그 제거,존재하지 않는 태그 필터,새로운 태그 DB 저장
     * 1. 중복으로 요청한 태그가 있다면 중복 태그를 제거한다.
     * 2. 수정하려는 게시글이 이미 가지고 있는 태그 Name 들을 가져온다
     * 3. 요청한 태그가 수정하려는 게시글이 이미 가지고 있는 태그 Name 들과 비교해 다른것들만 필터링
     * 4. 필터링된 태그들을 DB 에 저장
     */
    public void tagFilterAndSave(ArticleUpdateRequestDto updateDto, Article article) {
        // 중복으로 요청온 태그 제거
        // ex)) tag1,tag1,tag1,tag2 = tag1,tag2
        List<String> uniqueTags = validateDuplicatedTagRequest(updateDto);

        // 게시글에 이미 연결된 태그 리스트
        List<String> existingTags = getArticleTagLists(article);

        // 존재하지 않는 태그 필터
        List<String> filteredTags = notExistsTagsFilter(uniqueTags, existingTags);

        // 새로운 태그 DB 저장
        List<Tag> savedTags = addTags(filteredTags);

        // 게시글
        List<ArticleTag> articleTags = addArticleTags(article, savedTags);

        article.addArticleTag(articleTags);
    }

    // 한번에 똑같은 태그 요청 시 중복 태그 제거
    private List<String> validateDuplicatedTagRequest(ArticleUpdateRequestDto updateDto) {
        return updateDto.getTags()
            .stream()
            .distinct()
            .toList();
    }

    // 수정하려는 게시글의 모든 태그 Name 조회
    private List<String> getArticleTagLists(Article article) {
        return article.getArticleTags()
            .stream()
            .map(ArticleTag::getTag)
            .map(Tag::getName)
            .toList();
    }


    // 존재하지 않는 태그 필터
    private List<String> notExistsTagsFilter(List<String> targetTags, List<String> existingTags) {
        return targetTags.stream()
            .filter(tag -> !existingTags.contains(tag))
            .collect(Collectors.toList());
    }

    // Article 객체와 Tag 객체의 리스트를 받아서, 이 둘을 연결하는 ArticleTag 객체의 리스트를 생성
    private  List<ArticleTag> addArticleTags(Article article, List<Tag> addedTags) {
        return addedTags
            .stream()
            .map(tag -> new ArticleTag(article, tag))
            .collect(Collectors.toList());
    }

    private void verifyExistTag(String tagName) {
        boolean verifiedTag = tagRepository.existsByName(tagName);
        if (verifiedTag) {
            // 임시용 이기 때문에 409 아무거나
            // TODO : 예외 코드 추가하거나, 태그 생성 기능을 삭제하면 같이 삭제할 예정.
            throw new CustomException(LogicExceptionCode.MEMBER_ALREADY_EXISTS);
        }
    }

    // 이름으로 태그 가져오기
    private Tag fetchTagByName(String name) {
        Optional<Tag> optionalTag = tagRepository.findTagByName(name);

        return optionalTag
                .orElseThrow(() -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));
    }
}
