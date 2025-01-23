package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;

public enum ApplyStatus {
    APPLYING, FINISHED;

    public static ApplyStatus calculate(LocalDateTime startTime,
                                        long currentReservationCount,
                                        long maxReservationCount) {
        if (LocalDateTime.now().isAfter(startTime)) return FINISHED;
        return currentReservationCount < maxReservationCount ? APPLYING : FINISHED;
    }
}
