package be.dash.dashserver.core.fixture;

import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;

public class MemberFixture {
    private MemberFixture() {
    }

    public static Member createTeacher(long id) {
        return Member.builder()
                .id(id)
                .provider(SocialProvider.KAKAO)
                .socialId("socialId_67890")
                .socialName("facebook_user")
                .role(Role.TEACHER)
                .email("admin@example.com")
                .name("김영희")
                .phoneNumber("010-8765-4321")
                .nickname("yeonghee")
                .build();
    }

    public static Member createTeacherWithoutId() {
        return Member.builder()
                .provider(SocialProvider.KAKAO)
                .socialId("socialId_67890")
                .socialName("facebook_user")
                .role(Role.TEACHER)
                .email("admin@example.com")
                .name("김영희")
                .phoneNumber("010-8765-4321")
                .nickname("yeonghee")
                .build();
    }

    public static Member createMember(long id) {
        return Member.builder()
                .id(id)
                .provider(SocialProvider.KAKAO)
                .socialId("socialId_67890")
                .socialName("facebook_user")
                .role(Role.MEMBER)
                .email("admin@example.com")
                .name("김영희")
                .phoneNumber("010-8765-4321")
                .nickname("yeonghee")
                .build();
    }

    public static Member createTeacherWithNickname(String nickname, int index) {
        return Member.builder()
                .provider(SocialProvider.KAKAO)
                .socialId("socialId_67890")
                .socialName("facebook_user")
                .role(Role.TEACHER)
                .email("admin@example.com")
                .name("김영희")
                .phoneNumber("010-8765-432" + index)
                .nickname(nickname)
                .build();
    }
}
