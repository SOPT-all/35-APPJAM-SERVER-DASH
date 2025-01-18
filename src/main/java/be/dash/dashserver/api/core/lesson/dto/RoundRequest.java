package be.dash.dashserver.api.core.lesson.dto;

import java.time.LocalDateTime;

public record RoundRequest(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
