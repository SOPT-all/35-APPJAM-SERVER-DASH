package be.dash.dashserver.database.core.teacher;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.Teachers;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.fixture.LessonFixture;
import be.dash.dashserver.database.core.lesson.LessonRepositoryAdapter;
import be.dash.dashserver.database.core.member.MemberJpaEntity;
import be.dash.dashserver.database.core.member.MemberJpaRepositoy;
import be.dash.dashserver.database.fixture.MemberJpaEntityFixture;
import be.dash.dashserver.database.fixture.TeacherImageJpaEntityFixture;
import be.dash.dashserver.database.fixture.TeacherJpaEntityFixture;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TeacherRepositoryAdapter.class, LessonRepositoryAdapter.class})
class TeacherRepositoryAdapterTest {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository LessonRepository;
    @Autowired
    private TeacherJpaRepository teacherJpaRepository;
    @Autowired
    private TeacherImageJpaRepository teacherImageJpaRepository;
    @Autowired
    private MemberJpaRepositoy memberJpaRepositoy;

    @DisplayName("")
    @Test
    void findTeachersSortByLessonCountsDesc() {
        TeacherJpaEntity teacher = createTeacher("teacher", 1);
        TeacherJpaEntity teacher2 = createTeacher("teacher2", 2);
        TeacherJpaEntity teacher3 = createTeacher("teacher3", 3);
        LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 15, 3, 40, 50).minusDays(5);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 1, 15, 3, 40, 50).plusDays(10);
        createLessons(teacher, startDateTime, endDateTime);
        createLessons(teacher, startDateTime, endDateTime);
        createLessons(teacher2, startDateTime, endDateTime);
        createLessons(teacher3, startDateTime, endDateTime);
        createLessons(teacher3, startDateTime, endDateTime);
        createLessons(teacher3, startDateTime, endDateTime);

        Teachers teachersSortByLessonCountsDesc = teacherRepository.findTeachersSortByLessonCountsDesc();
        List<Long> list = teachersSortByLessonCountsDesc.teachers().stream().map(Teacher::getId).toList();

        assertThat(list).containsExactly(3L, 1L, 2L);
    }

    private TeacherJpaEntity createTeacher(String nickname, int index) {
        MemberJpaEntity memberJpaEntity = MemberJpaEntityFixture.createWithNickname(nickname, index);
        memberJpaRepositoy.save(memberJpaEntity);
        TeacherJpaEntity teacherEntity = TeacherJpaEntityFixture.createWithNickname(nickname, memberJpaEntity);
        teacherJpaRepository.save(teacherEntity);
        TeacherImageJpaEntity teacherImage = TeacherImageJpaEntityFixture.create(teacherEntity, "imageUrl");
        teacherImageJpaRepository.save(teacherImage);
        return teacherEntity;
    }

    private void createLessons(TeacherJpaEntity teacherJpaEntity, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), 1, Genre.HIPHOP, Level.ADVANCED,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        LessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        LessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
    }
}
