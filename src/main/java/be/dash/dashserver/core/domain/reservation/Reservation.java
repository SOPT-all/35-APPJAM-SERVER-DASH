package be.dash.dashserver.core.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {
    private final long id;
    private final long lessonId;
    private final long reservationId;
}
