package be.dash.dashserver.core.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private SocialProvider provider;
    private String socialId;
    private String socialName;
    private Role role;
    private String email;
    private String name;
    private String phoneNumber;
    private String nickname;
    private Student student;

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
            String nickname,
            Student student) {
        this.id = id;
        this.provider = provider;
        this.socialId = socialId;
        this.socialName = socialName;
        this.role = role;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.student = student;
    }

    public boolean isOnboarded() {
        return nickname != null;
    }
}
