package io.linkloud.api.global.security.auth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.linkloud.api.domain.member.repository.MemberRepository;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@Slf4j
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private final Key secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

}
