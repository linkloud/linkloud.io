package io.linkloud.api.domain.member.model;

import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken",timeToLive = 10000)
public class RefreshToken {

    @Id
    private final Long memberId;

    private final String refreshToken;



    public RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public void validateRefreshToken(String token) {
        if (!this.refreshToken.equals(token)) {
            throw new CustomException(AuthExceptionCode.REFRESH_TOKEN_INVALID);
        }
    }
}
