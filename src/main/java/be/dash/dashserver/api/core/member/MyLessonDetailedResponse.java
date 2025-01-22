package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.member.Member;

public record MyLessonDetailedResponse(long id,
                                       String name,
                                       String imageUrl,
                                       Genre genre,
                                       Level level,
                                       String location,
                                       String detailedAddress,
                                       LocalDateTime startDateTime,
                                       LocalDateTime endDateTime,
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
