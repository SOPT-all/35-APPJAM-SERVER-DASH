package be.dash.dashserver.api.core.lesson.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public record LessonRoundResponse(LocalDateTime startDateTime, LocalDateTime endDateTime) {

    public static long calculateRemainingDays(LocalDateTime startDateTime) {
        Duration duration = Duration.between(LocalDateTime.now(), startDateTime);
        return duration.toDays();
    }
}
