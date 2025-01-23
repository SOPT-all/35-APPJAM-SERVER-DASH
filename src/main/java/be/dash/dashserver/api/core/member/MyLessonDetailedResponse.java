package be.dash.dashserver.api.core.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import be.dash.dashserver.api.core.member.dto.ApplyStatus;
import be.dash.dashserver.api.core.member.dto.MemberReservationResponse;
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
                                       ApplyStatus applyStatus,
                                       List<MemberReservationResponse> students,
                                       int studentCount
) {
    public static MyLessonDetailedResponse from(Lesson lesson, List<Member> members, List<LocalDateTime> reservationDateTimes) {
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
                ApplyStatus.calculate(lesson.getStartTime(), lesson.getReservationCount(), lesson.getMaxReservationCount()),
                IntStream.range(0, members.size())
                        .mapToObj(i -> MemberReservationResponse.from(members.get(i), reservationDateTimes.get(i)))
                        .toList(),
                members.size()
        );
    }
}
