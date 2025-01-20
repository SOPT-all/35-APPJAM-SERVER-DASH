package be.dash.dashserver.database.core.reservation;

import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.reservation.service.ReservationRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    public boolean existsByMemberIdAndLessonId(long memberId, long lessonId) {
        return reservationJpaRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }
}
