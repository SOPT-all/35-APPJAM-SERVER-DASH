package be.dash.dashserver.core.domain.reservation;

public interface ReservationRepository {
    Reservations findAllByStudentId(long studentId);
}
