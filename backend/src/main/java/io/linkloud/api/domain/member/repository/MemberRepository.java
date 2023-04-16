package io.linkloud.api.domain.member.repository;

import io.linkloud.api.domain.member.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {


    /**
     * 이메일로 멤버 찾기
     */
    Optional<Member> findByEmail(String email);

    /**
     * 이메일,소셜 ID(PK) 으로 멤버 찾기
     */
    Optional<Member> findByEmailAndSocialId(String email, String socialId);

    /**
     * 중복된 닉네임
     */
    boolean existsByNickname(String nickname);

    /**
     * 중복된 이메일
     */
    boolean existsByEmail(String email);

    /**
     * jwt RefreshToken 으로 멤버 찾기
     */
    Optional<Member> findByRefreshToken(String refreshToken);

}
