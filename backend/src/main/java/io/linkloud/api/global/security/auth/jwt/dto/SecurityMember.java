package io.linkloud.api.global.security.auth.jwt.dto;

import io.linkloud.api.domain.member.model.SocialType;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class SecurityMember extends User {
    private Long id;

    private String picture;

    public SecurityMember(
        Long id,
        String nickname,
        Collection<? extends GrantedAuthority> authorities,
        String picture,
        SocialType socialType

    ) {
        super(nickname, socialType.name(), authorities);
        this.id = id;
        this.picture = picture;
    }

}
