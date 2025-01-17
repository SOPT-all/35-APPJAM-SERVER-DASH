package be.dash.dashserver.core.domain.member.command;

import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Student;

public record OnboardCommand(
        long id,
        String name,
        String phoneNumber,
        String nickname,
        Level level,
        List<Genre> genres,
        String profileImageUrl
) {
    public Member toMember() {
        return Member.builder()
                .id(id)
                .name(name)
                .phoneNumber(phoneNumber)
                .nickname(nickname)
                .student(Student.builder()
                        .level(level)
                        .genres(genres)
                        .profileImageUrl(profileImageUrl)
                        .build())
                .build();
    }
}
