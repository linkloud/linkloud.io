package io.linkloud.api.domain.member.model;


import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {

    @Id
    private final Long memberId;

    private final String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private final long refreshTokenExpiration;



    public RefreshToken(Long memberId, String refreshToken, long refreshTokenExpiration) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public void validateRefreshToken(String token) {
        if (!this.refreshToken.equals(token)) {
            throw new CustomException(AuthExceptionCode.INVALID_TOKEN);
        }
    }
}
