package be.dash.dashserver.database.core.teacher.projection;

import java.util.List;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.teacher.Teacher;

public record TeacherLessonCount(Long teacherId, String nickname, Long lessonCount) {
    public Teacher toDomain(List<String> imageUrls) {
        Member member = Member.builder().nickname(nickname).build();
        return Teacher.builder()
                .id(teacherId)
                .member(member)
                .lessonCount(lessonCount)
                .imageUrls(imageUrls)
                .build();
    }
}
