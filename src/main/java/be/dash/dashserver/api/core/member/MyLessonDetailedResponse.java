package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.reservation.Reservations;

public record MyLessonDetailedResponse(long lessonId,
                                       String lessonName,
                                       String lessonImageUrl,
                                       Genre lessonGenre,
                                       Level lessonLevel,
                                       String lessonLocation,
                                       String detailedAddress,
                                       LocalDateTime lessonStartDateTime,
                                       LocalDateTime lessonEndDateTime,
                                       APPLYSTATUS applyStatus,
                                       List<MemberReservationResponse> students,
                                       int studentCount
                                       ) {
    public static MyLessonDetailedResponse from(Lesson lesson, List<Member> members) {
        return new MyLessonDetailedResponse(
                lesson.getId(),
                lesson.getName(),
                lesson.getRepresentativeImageUrl(),
                lesson.getGenre(),
                lesson.getLevel(),
                lesson.getLocationName(),
                lesson.getDetailedAddress(),
                lesson.getRounds().getStartTime(),
                lesson.getRounds().getEndTime(),
                APPLYSTATUS.calculate(lesson.getStartTime(), lesson.getReservationCount(), lesson.getMaxReservationCount()),
                members.stream().map(MemberReservationResponse::from).toList(),
                members.size()
        );
    }
}
