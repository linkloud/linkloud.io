package io.linkloud.api.domain.tag.api;

import io.linkloud.api.domain.tag.Service.TagService;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.global.common.MultiDataResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService tagService;

    // Tag 리스트 임시 조회 api
    @GetMapping
    public ResponseEntity<?> getTags(
        @Positive @RequestParam int page,
        @RequestParam String sortBy) {
        // sortBy 옵션이 존재하는지 확인.
        Tag.SortBy sortField = tagService.verifySortField(sortBy);

        Page<TagDto.Response> tags = tagService.fetchTags(page, sortField.getSortBy());
        return ResponseEntity.ok().body(new MultiDataResponse<>(tags));
    }

    // Tag 리스트 임시 검색 api
    @GetMapping("/search")
    public ResponseEntity<?> getTagListBySearch() {
        return ResponseEntity.ok().build();
    }
}
