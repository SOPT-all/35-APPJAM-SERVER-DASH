package be.dash.dashserver.core.domain.member.service;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.AttendStatus;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.reservation.Reservations;

public record ReservationResult(
        long lessonId,
        long reservationId,
        String name,
        String imageUrl,
        String genre,
        String level,
        String location,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        int dDay,
        AttendStatus attendStatus
) {
    public static ReservationResult of(Lesson lesson, Reservations reservations) {
        return new ReservationResult(//첫번째 레슨과 reservations에 대해 다음을 한다.
                lesson.getId(),
                reservations.findReservationIdByLessonId(lesson.getId()),
                lesson.getTeacherNickName(),
                lesson.getRepresentativeImageUrl(),
                lesson.getGenre().name(),
                lesson.getLevel().name(),
                lesson.getLocationName(),
                lesson.getStartTime(),
                lesson.getEndTime(),
                lesson.calculateDDay(),
                AttendStatus.calculateAttendStatus(lesson.getStartTime(), lesson.getEndTime())

        );
    }

}
