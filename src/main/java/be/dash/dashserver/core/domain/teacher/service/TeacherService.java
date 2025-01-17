package be.dash.dashserver.core.domain.teacher.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.auth.JwtTokenGenerator;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.Teachers;
import be.dash.dashserver.core.domain.teacher.command.CreateTeacherCommand;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public List<TeacherLessonGenres> search() {
        Teachers teachers = teacherRepository.findTeachersSortByLessonCountsDesc();
        List<TeacherLessonGenres> teacherLessonGenres = new ArrayList<>();
        teachers.teachers().forEach(teacher -> {
            List<Genre> genres = lessonRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacher.getId());
            teacherLessonGenres.add(new TeacherLessonGenres(teacher, genres));
        });
        return teacherLessonGenres;
    }

    @Transactional
    public Token create(CreateTeacherCommand command) {
        Member member = memberRepository.findById(command.memberId());
        //1. 선생님을 저장한다는 것이 사실
        Teacher teacher = Teacher.builder()
                .member(member)
                .detail(command.detail())
                .educations(command.educations())
                .experiences(command.experiences())
                .instagram(command.instagram())
                .youtube(command.youtube())
                .imageUrls(command.imageUrls())
                .videoUrls(command.videoUrls())
                .build();
        teacherRepository.register(teacher);//선생님을 저장할 떄, 선생프로필, 선생이미지, 선생 비디오를 모두 저장해야한다.

        return new Token(jwtTokenGenerator.createAccessToken(String.valueOf(member.getId()), Role.TEACHER),
        jwtTokenGenerator.createRefreshToken(String.valueOf(member.getId()), Role.TEACHER));


    }
}
