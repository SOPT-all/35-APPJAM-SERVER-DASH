package be.dash.dashserver.core.domain.teacher.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Keyword;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.fixture.LessonFixture;
import be.dash.dashserver.core.fixture.MemberFixture;
import be.dash.dashserver.core.fixture.TeacherFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TeacherServiceTest extends ServiceSliceTest {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeacherImageRepository teacherImageRepository;

    @DisplayName("기본 정렬 조건에 맞게 댄서들을 정렬 후 조회한다.")
    @Test
    void search() {
        createLessons();

        List<TeacherLessonGenres> searched = teacherService.search(new Keyword());

        assertAll(
                () -> assertThat(searched.get(0).teacher().getId()).isEqualTo(1),
                () -> assertThat(searched.get(0).genres()).containsExactly(Genre.FEMALE_HIPHOP, Genre.HIPHOP),
                () -> assertThat(searched.get(1).teacher().getId()).isEqualTo(2),
                () -> assertThat(searched.get(1).genres()).containsExactly(Genre.HIPHOP),
                () -> assertThat(searched.get(2).teacher().getId()).isEqualTo(3),
                () -> assertThat(searched.get(2).genres()).containsExactly(Genre.FEMALE_HIPHOP)
        );
    }

    private void createLessons() {
        createTeacher(1);
        createTeacher(2);
        createTeacher(3);

        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.FEMALE_HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.FEMALE_HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.FEMALE_HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(2, 2, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(2, 2, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(2, 2, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(3, 3, Genre.FEMALE_HIPHOP, Level.BEGINNER));
    }

    private void createTeacher(long id) {
        Member memberWithoutId = MemberFixture.createTeacherWithNickname(String.valueOf(id), (int) id);
        memberRepository.save(memberWithoutId);
        Teacher teacherWithoutId = TeacherFixture.createWithoutId(id);
        teacherRepository.save(teacherWithoutId);
        Teacher teacher = TeacherFixture.create(id, id);
        teacherImageRepository.saveAll(teacher);
    }
}
