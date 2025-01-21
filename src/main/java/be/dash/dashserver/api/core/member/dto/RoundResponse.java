package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;

public record RoundResponse(LocalDateTime startDateTime,
                            LocalDateTime endDateTime) {
    public static RoundResponse from(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new RoundResponse(startDateTime, endDateTime);
    }
}
