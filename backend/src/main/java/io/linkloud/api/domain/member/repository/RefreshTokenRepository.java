package io.linkloud.api.domain.member.repository;

import io.linkloud.api.domain.member.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long> {

}
