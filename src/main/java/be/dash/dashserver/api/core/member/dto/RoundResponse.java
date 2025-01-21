package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.lesson.Round;

public record RoundResponse(LocalDateTime startDateTime,
                            LocalDateTime endDateTime) {
    public static RoundResponse from(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new RoundResponse(startDateTime, endDateTime);
    }
    public static RoundResponse from(Round round) {
        return new RoundResponse(round.getStartTime(), round.getEndTime());
    }
}
