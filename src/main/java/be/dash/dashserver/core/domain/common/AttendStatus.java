package be.dash.dashserver.core.domain.common;

import java.time.LocalDateTime;

public enum AttendStatus {
    ATTENDED, ATTENDING, NOTYET;

    public static AttendStatus calculateAttendStatus(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDateTime)) {
            return NOTYET;
        }
        if (now.isAfter(startDateTime) && now.isBefore(endDateTime)) {
            return ATTENDING;
        }
        return ATTENDED;
    }
}
