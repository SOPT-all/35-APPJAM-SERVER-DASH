package be.dash.dashserver.database.core.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.database.core.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialProvider provider;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private String socialName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true, unique = true)
    private String phoneNumber;

    @Column(nullable = true, unique = true)
    private String nickname;

    private MemberJpaEntity(SocialProvider provider, String socialId, String socialName, Role role, String email) {
        this.provider = provider;
        this.socialId = socialId;
        this.socialName = socialName;
        this.role = role;
        this.email = email;
    }

    public static MemberJpaEntity fromDomain(AuthMember authMember) {
        return new MemberJpaEntity(authMember.getSocialProvider(),
                authMember.getSocialId(),
                authMember.getSocialName(),
                authMember.getRole(),
                authMember.getEmail());
    }

    AuthMember toAuthMember() {
        return AuthMember.createWithId(id, provider, socialId, email, socialName);
    }

    @Builder
    public MemberJpaEntity(SocialProvider provider, String socialId, String socialName, Role role, String email, String name, String phoneNumber, String nickname) {
        this.provider = provider;
        this.socialId = socialId;
        this.socialName = socialName;
        this.role = role;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }

    public MemberJpaEntity(Member member) {
        this.id = member.getId();
        this.provider = member.getProvider();
        this.socialId = member.getSocialId();
        this.socialName = member.getSocialName();
        this.role = member.getRole();
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.nickname = member.getNickname();
    }

    public Member toDomain() {
        return Member.builder()
                .id(id)
                .provider(provider)
                .socialId(socialId)
                .socialName(socialName)
                .role(role)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .build();
    }

    public void updateOnboardDetails(Member member) {
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.nickname = member.getNickname();
    }
}
