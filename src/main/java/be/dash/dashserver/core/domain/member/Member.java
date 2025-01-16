package be.dash.dashserver.core.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final SocialProvider provider;
    private final String socialId;
    private final String socialName;
    private final Role role;
    private final String email;
    private final String name;
    private final String phoneNumber;
    private final String nickname;

    @Builder
    public Member(
            Long id,
            SocialProvider provider,
            String socialId,
            String socialName,
            Role role,
            String email,
            String name,
            String phoneNumber,
            String nickname) {
        this.id = id;
        this.provider = provider;
        this.socialId = socialId;
        this.socialName = socialName;
        this.role = role;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }
}
