package be.dash.dashserver.core.domain.reservation.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public boolean isBooked(long memberId, long lessonId) {
        return reservationRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }

    public long reserve(long memberId, long lessonId) {
        return reservationRepository.save(memberId, lessonId);
    }
}
