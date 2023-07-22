package io.linkloud.api.domain.member.repository;

import io.linkloud.api.domain.tag.dto.MemberTagsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<MemberTagsDto> findMyTagsByMemberId(Long memberId, Pageable pageable);

}

