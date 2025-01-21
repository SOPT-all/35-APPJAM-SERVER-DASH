package be.dash.dashserver.core.domain.reservation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservations {
    private List<Reservation> reservations;

    public long findReservationIdByLessonId(long id) {
        return reservations.stream().filter(reservation -> reservation.getLessonId() == id)
                .findFirst().get().getId();
    }

    public int getSize() {
        return reservations.size();
    }

    public Set<Long> getLessonIds() {
        return reservations.stream().map(Reservation::getLessonId).collect(Collectors.toSet());
    }
}
