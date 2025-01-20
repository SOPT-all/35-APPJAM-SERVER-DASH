package be.dash.dashserver.api.core.lesson.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.lesson.Lesson;

public enum LessonStatus {
    EXPIRED,
    OPEN,
    OVER_BOOKED;

    public static LessonStatus from(Lesson lesson) {
        if (lesson.getStartTime().isBefore(LocalDateTime.now())) {
            return EXPIRED;
        }
        return checkOverBook(lesson);
    }

    private static LessonStatus checkOverBook(Lesson lesson) {
        Long maxReservationCount = lesson.getMaxReservationCount();
        Long reservationCount = lesson.getReservationCount();
        if (reservationCount >= maxReservationCount) {
            return OVER_BOOKED;
        }
        return OPEN;
    }
}
