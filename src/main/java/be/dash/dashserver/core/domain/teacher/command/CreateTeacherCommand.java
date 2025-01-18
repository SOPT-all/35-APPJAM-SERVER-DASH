package be.dash.dashserver.core.domain.teacher.command;

import java.util.List;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.teacher.Teacher;

public record CreateTeacherCommand(long memberId,
                                   String instagram,
                                   String youtube,
                                   List<String> educations,
                                   List<String> experiences,
                                   String detail,
                                   List<String> imageUrls,
                                   List<String> videoUrls) {

    public Teacher toDomain(Member member) {
        return Teacher.builder()
                .member(member)
                .detail(detail())
                .educations(educations())
                .experiences(experiences())
                .instagram(instagram())
                .youtube(youtube())
                .imageUrls(imageUrls())
                .videoUrls(videoUrls())
                .build();
    }
}
