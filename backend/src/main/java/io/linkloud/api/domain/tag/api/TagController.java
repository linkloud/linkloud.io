package io.linkloud.api.domain.tag.api;

import io.linkloud.api.domain.tag.Service.TagService;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<?> postArticleTag() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Tag 리스트 임시 조회 api
    @GetMapping
    public ResponseEntity<?> getTags() {
        return ResponseEntity.ok().build();
    }

    // Tag 리스트 임시 검색 api
    @GetMapping("/search")
    public ResponseEntity<?> getTagListBySearch() {
        return ResponseEntity.ok().build();
    }
}
