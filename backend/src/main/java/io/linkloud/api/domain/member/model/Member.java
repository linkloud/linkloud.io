package io.linkloud.api.domain.member.model;


import io.linkloud.api.global.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // new Member() 작성이 불가하기 때문에 객체 자체의 일관성 유지력을 높일 수 있다.
@Table(name = "member")
@Getter
@Entity
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    //  닉네임
    @Column(length = 10, unique = true)
    private String nickname;

    // google
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // oauth2 PK (google : sub, kakao or naver : id)
    @Column(name = "social_Id")
    private String socialId;

    /**
     * 프로필 사진
     * */
    @Column
    private String picture;

    // jwt 리프레시 토큰
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private Role role;

    // 리프레시 토큰 업데이트
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}


