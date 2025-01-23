package be.dash.dashserver.database.core.reservation;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;
import be.dash.dashserver.core.domain.reservation.service.ReservationRepository;
import be.dash.dashserver.database.core.lesson.LessonJpaEntityRepository;
import be.dash.dashserver.database.core.student.StudentJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final StudentJpaRepository studentJpaRepository;

    @Override
    public boolean existsByMemberIdAndLessonId(long memberId, long lessonId) {
        return reservationJpaRepository.existsByMemberIdAndLessonId(memberId, lessonId);

    }

    @Override
    public Reservations findAllByStudentId(long studentId) {
        return new Reservations(reservationJpaRepository.findAllByStudentId(studentId).stream()
                .map(ReservationJpaEntity::toDomain).toList());
    }

    @Override
    public Optional<Reservation> findById(long reservationId) {
        return reservationJpaRepository.findById(reservationId).map(ReservationJpaEntity::toDomain);
    }

    @Override
    public Reservations findAllByLessonIdOrderByCreatedAtDesc(Long lessonId) {
        return new Reservations(reservationJpaRepository.findAllByLessonIdOrderByCreatedAtDesc(lessonId).stream()
                .map(ReservationJpaEntity::toDomain).toList());
    }

    @Override
    public long save(long studentId, long lessonId) {
        ReservationJpaEntity reservationJpaEntity = new ReservationJpaEntity(lessonId, studentId);
        reservationJpaRepository.save(reservationJpaEntity);
        return reservationJpaEntity.getId();
    }
}
