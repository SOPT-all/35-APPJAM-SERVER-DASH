package be.dash.dashserver.core.domain.member;

import lombok.Getter;

@Getter
public class AuthMember {

    private long id;
    private SocialProvider socialProvider;
    private String socialId;
    private String email;
    private String socialName;
    private Role role;

    private AuthMember(SocialProvider socialProvider, String socialId, String email, String socialName, Role role) {
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.email = email;
        this.socialName = socialName;
        this.role = role;
    }

    private AuthMember(long id, SocialProvider socialProvider, String socialId, String email, String socialName, Role role) {
        this.id = id;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.email = email;
        this.socialName = socialName;
        this.role = role;
    }

    public static AuthMember create(SocialProvider socialProvider,
                                    String socialId,
                                    String email,
                                    String socialName) {
        return new AuthMember(socialProvider, socialId, email, socialName, Role.MEMBER);
    }

    public static AuthMember createWithId(long id,
                                          SocialProvider socialProvider,
                                          String socialId,
                                          String email,
                                          String socialName) {
        return new AuthMember(id, socialProvider, socialId, email, socialName, Role.MEMBER);
    }
}
