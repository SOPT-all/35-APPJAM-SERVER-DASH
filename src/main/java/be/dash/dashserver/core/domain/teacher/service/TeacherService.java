package be.dash.dashserver.core.domain.teacher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.auth.JwtTokenGenerator;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Keyword;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.Teachers;
import be.dash.dashserver.core.domain.teacher.command.CreateTeacherCommand;
import be.dash.dashserver.core.domain.teacher.service.dto.TeacherDetailResult;
import be.dash.dashserver.core.log.annotation.Trace;
import lombok.RequiredArgsConstructor;

import static be.dash.dashserver.core.domain.common.Keyword.ANY;

@Trace
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public List<TeacherLessonGenres> search(Keyword keyword) {
        Teachers teachers = teacherRepository.findTeachersSortByLessonCountsDesc(keyword.getValue());
        return getTeacherLessonGenres(teachers);
    }

    private List<TeacherLessonGenres> getTeacherLessonGenres(Teachers teachers) {
        List<TeacherLessonGenres> teacherGenres = new ArrayList<>();
        teachers.teachers().forEach(teacher -> {
            List<Genre> genres = lessonRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacher.getId());
            TeacherLessonGenres teacherLessonGenres = new TeacherLessonGenres(teacher, genres);
            teacherGenres.add(teacherLessonGenres);
        });
        return teacherGenres;
    }

    @Transactional
    public Token create(CreateTeacherCommand command) {
        Member member = memberRepository.findById(command.memberId());
        Teacher teacher = command.toDomain(member);
        teacherRepository.register(teacher);
        memberRepository.updateRole(member.getId(), Role.TEACHER);

        return new Token(jwtTokenGenerator.createAccessToken(String.valueOf(member.getId()), Role.TEACHER),
                jwtTokenGenerator.createRefreshToken(String.valueOf(member.getId()), Role.TEACHER));
    }

    public List<TeacherLessonGenres> popular() {
        Teachers teachers = teacherRepository.findTeachersSortByLessonCountsDesc(ANY);
        return getTeacherLessonGenres(teachers);
    }

    public TeacherDetailResult find(long teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId);
        List<Genre> genres = lessonRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacher.getId());
        Lessons activeLessonsByTeacher = lessonRepository.findLessonsByTeacher(teacher, LocalDateTime.now());
        // TODO: TeacherEntity가 현재는 member를 가지지만 memberId로 뺄것이기 대문에 지연로딩을 이용하지 않겠다. (memberId는 리팩터링 후에도 Teacher가 가지고 있다.)
        Member member = memberRepository.findById(teacher.getMember().getId());
        return new TeacherDetailResult(new TeacherLessonGenres(teacher, genres), member.getNickname(), activeLessonsByTeacher);
    }
}
