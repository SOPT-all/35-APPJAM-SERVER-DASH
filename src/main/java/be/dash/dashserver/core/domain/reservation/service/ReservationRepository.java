package be.dash.dashserver.core.domain.reservation.service;

import java.util.Optional;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;

public interface ReservationRepository {

    boolean existsByMemberIdAndLessonId(long memberId, long lessonId);

    long save(long studentId, long lessonId);

    Reservations findAllByStudentId(long studentId);

    Optional<Reservation> findById(long reservationId);

    Reservations findAllByLessonIdOrderByCreatedAtDesc(Long lessonId);
}
