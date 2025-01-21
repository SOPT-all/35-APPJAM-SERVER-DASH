package be.dash.dashserver.api.core.lesson.dto;

import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.member.Member;

public record LessonReservationResponse(
        String imageUrl,
        String name,
        String teacherNickname,
        long price,
        String detail,
        Level level,
        String location,
        String locationDetail,
        LessonRoundResponses lessonRound,
        String studentName
) {
    public LessonReservationResponse(Lesson lesson, Member member) {
        this(
                lesson.getRepresentativeImageUrl(),
                lesson.getName(),
                lesson.getTeacher().getNickName(),
                lesson.getPrice(),
                lesson.getDetail(),
                lesson.getLevel(),
                lesson.getLocation().getTitle(),
                lesson.getLocation().getDetailedAddress(),
                LessonRoundResponses.from(lesson.getRounds()),
                member.getName()
        );
    }

}
