package io.linkloud.api.domain.member.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

}
