package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.AttendStatus;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.member.service.ReservationResult;

public record ReservationResponse(long lessonId,
                                  long reservationId,
                                  String name,
                                  String imageUrl,
                                  Genre genre,
                                  Level level,
                                  String location,
                                  LocalDateTime startDateTime,
                                  LocalDateTime endDateTime,
                                  int dDay,
                                  AttendStatus attendStatus) {
    public static ReservationResponse from(ReservationResult reservationResult) {
        return new ReservationResponse(
                reservationResult.lessonId(),
                reservationResult.reservationId(),
                reservationResult.name(),
                reservationResult.imageUrl(),
                Genre.valueOf(reservationResult.genre()),
                Level.valueOf(reservationResult.level()),
                reservationResult.location(),
                reservationResult.startDateTime(),
                reservationResult.endDateTime(),
                reservationResult.dDay(),
                reservationResult.attendStatus()
        );
    }
}
