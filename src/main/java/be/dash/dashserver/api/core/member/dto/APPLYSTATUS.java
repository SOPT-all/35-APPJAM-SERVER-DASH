package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;

public enum APPLYSTATUS {
    APPLYING, FINISHED;

    public static APPLYSTATUS calculate(LocalDateTime startTime,
                                        long currentReservationCount,
                                        long maxReservationCount) {
        if (LocalDateTime.now().isAfter(startTime)) return FINISHED;
        return currentReservationCount < maxReservationCount ? APPLYING : FINISHED;
    }
}
