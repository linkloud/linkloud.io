package io.linkloud.api.domain.heart.api;

import io.linkloud.api.domain.heart.dto.HeartRequestDto;
import io.linkloud.api.domain.heart.dto.HeartResponseDto;
import io.linkloud.api.domain.heart.service.HeartService;
import io.linkloud.api.global.common.SingleDataResponse;
import io.linkloud.api.global.security.resolver.LoginMemberId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/heart")
public class HeartController {

    private final HeartService heartService;

    /** 좋아요 추가 */
    @PostMapping("/{articleId}")
    public ResponseEntity<SingleDataResponse<HeartResponseDto>> createHeart(
        @LoginMemberId Long memberId,
        @PathVariable @Valid Long articleId,
        @RequestBody @Valid HeartRequestDto heartRequestDto) {

        // 프론트엔드에서 게시글의 좋아요가 눌러진 상태를 안다면
        // 좋아요 추가, 좋아요 삭제를 따로 나눠서 할 수 있음
        HeartResponseDto heartResponseDto = heartService.addHeart(memberId, articleId, heartRequestDto);

        return ResponseEntity.ok(new SingleDataResponse<>(heartResponseDto));
    }

    /** 좋아요 취소 */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteHeart(@LoginMemberId Long memberId, @PathVariable @Valid Long articleId) {

        heartService.removeHeart(memberId, articleId);

        return ResponseEntity.noContent().build();
    }

}
