package be.dash.dashserver.core.domain.reservation.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

@Trace
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;

    public Reservation findById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("예약을 찾을 수 없습니다."));
    }

    public boolean isBooked(long memberId, long lessonId) {
        return reservationRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }

    @Transactional
    public long reserve(long memberId, long lessonId) {
        Student student = memberRepository.findStudentByMemberId(memberId);
        return reservationRepository.save(student.getId(), lessonId);
    }

    public Reservations findAllByLessonIdOrderByCreatedAtDesc(Long lessonId) {
        return reservationRepository.findAllByLessonIdOrderByCreatedAtDesc(lessonId);
    }
}
