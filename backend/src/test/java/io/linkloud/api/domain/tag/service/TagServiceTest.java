package io.linkloud.api.domain.tag.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    TagDto.Response tag_spring = new TagDto.Response(1L, "spring", 3L);
    TagDto.Response tag_spring_boot = new TagDto.Response(2L, "spring-boot", 4L);

    List<TagDto.Response> tags = List.of(
        tag_spring,
        tag_spring_boot
    );

    Page<TagDto.Response> tagResponse = new PageImpl<>(tags);

    @DisplayName("인기순 태그들 조회")
    @Test
    void fetchTagsSortByPopularity() {
        given(tagRepository.findAllOrderBy(Mockito.any(PageRequest.class)))
            .willReturn(tagResponse);

        // fetchTags()가 예외를 발생하지 않아야 함.
        assertDoesNotThrow(() -> tagService.fetchTags(1,5,"popularity"));
    }

    @DisplayName("최신순 태그들 조회")
    @Test
    void fetchTagsSortByCreatedAt() {
        given(tagRepository.findAllOrderBy(Mockito.any(PageRequest.class)))
            .willReturn(tagResponse);

        // fetchTags()가 예외를 발생하지 않아야 함.
        assertDoesNotThrow(() -> tagService.fetchTags(1,5,"createdAt"));
    }

    @DisplayName("이름순 태그들 조회")
    @Test
    void fetchTagsSortByName() {
        given(tagRepository.findAllOrderBy(Mockito.any(PageRequest.class)))
            .willReturn(tagResponse);

        // fetchTags()가 예외를 발생하지 않아야 함.
        assertDoesNotThrow(() -> tagService.fetchTags(1,5,"name"));
    }

    @DisplayName("태그 검색")
    @Test
    void fetchTagsBySearch() {
        given(tagRepository.findTagByNameIsStartingWith(Mockito.any(String.class)))
            .willReturn(tags);

        assertDoesNotThrow(() -> tagService.fetchTagListBySearch("sp"));
    }
}
