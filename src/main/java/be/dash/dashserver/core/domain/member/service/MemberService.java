package be.dash.dashserver.core.domain.member.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;
import be.dash.dashserver.core.domain.reservation.service.ReservationRepository;
import be.dash.dashserver.core.log.annotation.Trace;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;

@Trace
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LessonRepository lessonRepository;
    private final ReservationRepository reservationRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public void onboard(OnboardCommand command) {
        Member member = command.toMember();
        memberRepository.onboard(member);
    }

    @Transactional(readOnly = true)
    public MemberInformationResult getMemberInformation(Long memberId) {
        Member member = memberRepository.findById(memberId);
        Student student = memberRepository.findStudentByMemberId(memberId);
        return new MemberInformationResult(
                member.getNickname(),
                student.getProfileImageUrl(),
                memberRepository.getReservationCountByStudentId(student.getId()),
                memberRepository.getFavoriteCountByStudentId(memberId),
                lessonRepository.getLessonCount(memberId)
        );
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public List<ReservationResult> getMemberReservations(Long memberId) {
        long studentId = memberRepository.findStudentByMemberId(memberId).getId();
        Reservations reservations = reservationRepository.findAllByStudentId(studentId);
        List<Long> list = reservations.getReservations().stream().map(Reservation::getId).toList();
        Set<Long> lessonIds1 = reservations.getLessonIds();
        for (long l : list) {
            System.out.println(l);
        }
        for (long l : lessonIds1) {
            System.out.println(l);
        }
        Set<Long> lessonIds = reservations.getLessonIds();
        List<Lesson> myLessons = lessonRepository.findAllByIdsOrderByStartDate(lessonIds);
        return myLessons.stream()
                .map(lesson -> ReservationResult.of(lesson, reservations))
                .toList();
    }

    public Teacher findTeacherByMemberId(Long memberId) {
        return teacherRepository.findByMemberId(memberId)
                .orElseThrow(() -> new ForbiddenException("해당하는 선생님을 찾을 수 없습니다."));
    }

    public List<Member> findAllByIds(List<Long> memberIds) {
        return memberRepository.findAllByIds(memberIds);
    }
}
