package io.linkloud.api.domain.tag.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    // Tag 임시 생성 api
    @PostMapping
    public ResponseEntity<?> createTag() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Article 임시 생성 api
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
