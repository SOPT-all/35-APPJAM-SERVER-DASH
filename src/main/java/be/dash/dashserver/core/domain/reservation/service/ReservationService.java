package be.dash.dashserver.core.domain.reservation.service;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public boolean isBooked(long memberId, long lessonId) {
        return reservationRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }
}
