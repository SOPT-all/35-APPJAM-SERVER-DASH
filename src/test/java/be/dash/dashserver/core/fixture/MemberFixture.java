package be.dash.dashserver.core.fixture;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.core.domain.member.Student;

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

    public static Member createStudentWithoutId(String nickname, Genre genre, Level level, String phoneNumber) {
        return Member.builder()
                .provider(SocialProvider.KAKAO)
                .socialId("socialId_67890")
                .socialName("facebook_user")
                .role(Role.MEMBER)
                .email("admin@example.com")
                .name("김영희")
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .student(Student.builder()
                        .profileImageUrl(phoneNumber)
                        .genres(List.of(genre))
                        .level(level)
                        .build())
                .build();
    }
}
