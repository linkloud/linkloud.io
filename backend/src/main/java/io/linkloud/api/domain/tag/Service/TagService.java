package io.linkloud.api.domain.tag.Service;

import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.repository.TagRepository;
import io.linkloud.api.global.exception.ExceptionCode;
import io.linkloud.api.global.exception.LogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public void addTag(Tag tag) {
        verifyExistTag(tag.getName());
        Tag addedTag = tagRepository.save(tag);

        log.info("Tag 생성 완료.");
    }

    public void addArticleTag() {}

    public void fetchTags() {}

    public void fetchTagListBySearch() {}

    private void verifyExistTag(String tagName) {
        boolean verifiedTag = tagRepository.existsByName(tagName);
        if(verifiedTag) {
            // 임시용 이기 때문에 409 아무거나
            // TODO : 예외 코드 추가하거나, 태그 생성 기능을 삭제하면 같이 삭제할 예정.
            throw new LogicException(ExceptionCode.MEMBER_ALREADY_EXISTS);
        }
    }
}
