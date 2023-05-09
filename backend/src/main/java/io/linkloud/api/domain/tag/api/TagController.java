package io.linkloud.api.domain.tag.api;

import io.linkloud.api.domain.tag.Service.TagService;
import io.linkloud.api.domain.tag.dto.ArticleTagDto;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.mapper.TagMapper;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.global.common.MultiDataResponse;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper mapper;

    // Tag 임시 생성 api
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDto.Post post) {
        tagService.addTag(mapper.postDtoToTag(post));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Article-Tag 임시 생성 api\
    // TODO: Article 기능 완성되면 통합해야 함.
    @PostMapping("/article-tag")
    public ResponseEntity<?> createArticleTag(@RequestBody ArticleTagDto.Post post) {
        tagService.addArticleTag(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Tag 리스트 임시 조회 api
    @GetMapping
    public ResponseEntity<?> getTags(@RequestParam int page, @RequestParam String sortBy) {
        // sortBy 옵션이 존재하는지 확인.
        Tag.SortBy sortField = tagService.verifyOrderBy(sortBy);

        Page<TagDto.Response> tags = tagService.fetchTags(page, sortField.getSortBy());
        return ResponseEntity.ok().body(new MultiDataResponse<>(tags.getContent(), tags));
    }

    // Tag 리스트 임시 검색 api
    @GetMapping("/search")
    public ResponseEntity<?> getTagListBySearch() {
        return ResponseEntity.ok().build();
    }
}
