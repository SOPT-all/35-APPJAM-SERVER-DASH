package be.dash.dashserver.core.domain.reservation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservations {
    private List<Reservation> reservations;

    public long findReservationIdByLessonId(long id) {
        long reservationId = reservations.stream().filter(reservation -> reservation.getLessonId() == id)
                .findFirst().get().getReservationId();
        return reservationId;
    }

    public int getSize() {
        return reservations.size();
    }
}
