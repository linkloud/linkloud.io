package io.linkloud.api.domain.tag.service;

import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.repository.TagRepository;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.exception.CustomException;
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

    // 태그들 저장
    public List<Tag> addTags(List<String> tagNames) {
        List<Tag> tags = new ArrayList<>();

        for (String tagName : tagNames) {
            Tag tag = addtag(tagName);
            tags.add(tag);
        }

        return tags;
    }

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
