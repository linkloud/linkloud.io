package io.linkloud.api.domain.member.repository;

import io.linkloud.api.domain.member.model.Member;


import io.linkloud.api.domain.member.model.SocialType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import io.linkloud.api.domain.member.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {


    /**
     * 이메일,소셜 ID(PK) 으로 멤버 찾기
     */
    Optional<Member> findByEmailAndSocialId(String email, String socialId);

    Optional<Member>findMemberBySocialTypeAndSocialId(SocialType socialType, String socialId);

    /**
     * 중복된 닉네임
     */
    boolean existsByNickname(String nickname);

    /**
     * 3일전에 가입한 회원 권한 MEMBER 로 변경
     * @param threeDaysAgo 3일전
     * @param role        회원권한
     * @return            권한이 변경된 회원들
     */
    List<Member> findByCreatedAtBeforeAndRole(LocalDateTime threeDaysAgo, Role role);
}
