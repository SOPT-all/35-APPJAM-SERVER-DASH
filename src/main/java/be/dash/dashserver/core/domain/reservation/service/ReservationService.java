package be.dash.dashserver.core.domain.reservation.service;


import org.springframework.stereotype.Service;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation findById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("예약을 찾을 수 없습니다."));
    }

    public boolean isBooked(long memberId, long lessonId) {
        return reservationRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }

    public long reserve(long memberId, long lessonId) {
        return reservationRepository.save(memberId, lessonId);
    }
}
