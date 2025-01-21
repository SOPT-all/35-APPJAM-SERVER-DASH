package be.dash.dashserver.core.domain.reservation;

import java.util.Optional;

public interface ReservationRepository {
    boolean existsByMemberIdAndLessonId(long memberId, long lessonId);
    Reservations findAllByStudentId(long studentId);

    Optional<Reservation> findById(long reservationId);
}
