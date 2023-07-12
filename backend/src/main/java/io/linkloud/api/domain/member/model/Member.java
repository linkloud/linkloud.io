package io.linkloud.api.domain.member.model;


import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    @Column(length = 20, unique = true)
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


    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Article> myArticles = new ArrayList<>();
    // 닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    // 권한 변경
    public void updateRoleToMember(Role role) {
        this.role = role;
    }
}


