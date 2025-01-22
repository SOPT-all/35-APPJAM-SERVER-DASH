package be.dash.dashserver.api.core.member;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.api.core.member.dto.MyLessonDetailedResponse;
import be.dash.dashserver.api.core.member.dto.MyLessonResponse;
import be.dash.dashserver.api.core.member.dto.MyLessonsResponse;
import be.dash.dashserver.api.core.member.dto.ReservationDetailedResponse;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.service.LessonService;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.MemberService;
import be.dash.dashserver.core.domain.member.service.StudentService;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.core.domain.reservation.Reservations;
import be.dash.dashserver.core.domain.reservation.service.ReservationService;
import be.dash.dashserver.core.domain.teacher.Teacher;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    private final MemberService memberService;
    private final ReservationService reservationService;
    private final LessonService lessonService;
    private final StudentService studentsService;

    @Transactional(readOnly = true)
    public ReservationDetailedResponse getMemberReservation(long memberId, long reservationId) {
        Member member = memberService.findById(memberId);
        Reservation reservation = reservationService.findById(reservationId);
        Lesson lesson = lessonService.findById(reservation.getLessonId());
        return ReservationDetailedResponse.from(member, reservation, lesson);
    }

    @Transactional(readOnly = true)
    public MyLessonsResponse getMemberLessons(long memberId) {
        Teacher teacher = memberService.findTeacherByMemberId(memberId);
        List<Lesson> lessons = lessonService.findAllByTeacherId(teacher.getId());
        return MyLessonsResponse.from(lessons.stream().map(MyLessonResponse::from).toList());
    }

    @Transactional(readOnly = true)
    public MyLessonDetailedResponse getMyLesson(long memberId, long lessonId) {
        Teacher teacher = memberService.findTeacherByMemberId(memberId);
        lessonService.validateOwner(teacher.getId(), lessonId);
        Lesson lesson = lessonService.findById(lessonId);
        Reservations reservations = reservationService.findAllByLessonIdOrderByCreatedAtDesc(lessonId);
        List<Long> memberIds = studentsService.findAllByIds(reservations.getStudentIds()).stream().map(Student::getId).toList();
        List<Member> members = memberService.findAllByIds(memberIds);
        return MyLessonDetailedResponse.from(lesson, members);
    }
}
