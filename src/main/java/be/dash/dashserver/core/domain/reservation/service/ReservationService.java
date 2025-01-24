package be.dash.dashserver.core.domain.reservation.service;


import javax.print.DocFlavor.STRING;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.payment.PaymentClientApi;
import be.dash.dashserver.core.domain.payment.dto.PaymentResult;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;
import be.dash.dashserver.core.domain.reservation.command.CreateReservationCommand;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.exception.ConflictException;
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
    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;
    private final PaymentClientApi paymentClientApi;

    public Reservation findById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("예약을 찾을 수 없습니다."));
    }

    public boolean isBooked(long memberId, long lessonId) {
        return reservationRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }

    @Transactional
    public long reserve(CreateReservationCommand command) {
        validateReservationAvailability(command);
        Student student = memberRepository.findStudentByMemberId(command.memberId());
        paymentClientApi.purchase(command.toPaymentInformation());
        long reservationId;
        try {
            reservationId = reservationRepository.save(student.getId(), command.lessonId());
        } catch (DataIntegrityViolationException e) {
            paymentClientApi.cancelByInternalError(command.paymentKey());
            throw new ConflictException("이미 예약된 수업입니다.");
        }
        lessonRepository.increaseReservationCount(command.lessonId());
        return reservationId;
    }

    private void validateReservationAvailability(CreateReservationCommand command) {
        validateOwnerIfTeacher(command.memberId(), command.lessonId());
        if (lessonRepository.isFull(command.lessonId())) {
            throw new ConflictException("잔여 좌석이 없습니다.");
        }
    }


    private void validateOwnerIfTeacher(long memberId, long lessonId) {
        teacherRepository.findByMemberId(memberId)
                .ifPresent(teacher -> {
                    validateOwner(teacher.getId(), lessonId);
                });
    }

    public Reservations findAllByLessonIdOrderByCreatedAtDesc(Long lessonId) {
        return reservationRepository.findAllByLessonIdOrderByCreatedAtDesc(lessonId);
    }

    private void validateOwner(long teacherId, long lessonId) {
        if (lessonRepository.existsByTeacherIdAndLessonId(teacherId, lessonId)) {
            throw new ConflictException("본인 수업은 수강할 수 없습니다.");
        }
    }
}
