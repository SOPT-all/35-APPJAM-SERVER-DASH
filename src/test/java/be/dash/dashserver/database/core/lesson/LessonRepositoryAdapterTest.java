package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.fixture.LessonFixture;
import be.dash.dashserver.database.core.member.MemberJpaEntity;
import be.dash.dashserver.database.core.member.MemberJpaRepository;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaRepository;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaRepository;
import be.dash.dashserver.database.fixture.MemberJpaEntityFixture;
import be.dash.dashserver.database.fixture.TeacherImageJpaEntityFixture;
import be.dash.dashserver.database.fixture.TeacherJpaEntityFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@Import(LessonRepositoryAdapter.class)
class LessonRepositoryAdapterTest {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TeacherJpaRepository teacherJpaRepository;
    @Autowired
    private TeacherImageJpaRepository teacherImageJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private LessonRoundJpaRepository lessonRoundJpaRepository;
    @Autowired
    private LessonImageJpaRepository lessonImageJpaRepository;
    @Autowired
    private LessonVideoJpaRepository lessonVideoJpaRepository;

    @DisplayName("동적으로 필터에 해당하며, 마감기한이 지나지 않은 수업들을 조회한다.")
    @Test
    void findActiveLessonsByFilters() {
        LocalDateTime startDateTime = LocalDateTime.of(2025, 1, 15, 3, 40, 50).minusDays(5);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 1, 15, 3, 40, 50).plusDays(10);
        TeacherJpaEntity teacher = createTeacher();
        createLessons(teacher, startDateTime, endDateTime);
        LocalDateTime now = LocalDateTime.of(2025, 1, 15, 3, 40, 50);

        List<Lesson> lessonsHiphopBeginners = lessonRepository.findActiveLessonsByFilters(Genre.HIPHOP, Level.BEGINNER, startDateTime, endDateTime, now);
        List<Lesson> lessonsFemaleHiphopBeginners = lessonRepository.findActiveLessonsByFilters(Genre.FEMALE_HIPHOP, Level.BEGINNER, startDateTime, endDateTime, now);
        List<Lesson> lessonsFemaleHiphopAdvanced = lessonRepository.findActiveLessonsByFilters(Genre.HIPHOP, Level.ADVANCED, startDateTime, endDateTime, now);

        assertAll(
                () -> assertThat(lessonsHiphopBeginners.size()).isEqualTo(3),
                () -> assertThat(lessonsFemaleHiphopBeginners.size()).isEqualTo(1),
                () -> assertThat(lessonsFemaleHiphopAdvanced.size()).isEqualTo(1)
        );
    }

    private void createLessons(TeacherJpaEntity teacherJpaEntity, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(3), 10));
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.FEMALE_HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.HIPHOP, Level.ADVANCED,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        lessonRepository.save(LessonFixture.create(teacherJpaEntity.getId(), teacherJpaEntity.getMember().getId(), Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
    }

    private TeacherJpaEntity createTeacher() {
        MemberJpaEntity memberJpaEntity = MemberJpaEntityFixture.create();
        memberJpaRepository.save(memberJpaEntity);
        TeacherJpaEntity teacherEntity = TeacherJpaEntityFixture.create(memberJpaEntity);
        teacherJpaRepository.save(teacherEntity);
        TeacherImageJpaEntity teacherImage = TeacherImageJpaEntityFixture.create(teacherEntity, "imageUrl");
        teacherImageJpaRepository.save(teacherImage);
        return teacherEntity;
    }

    @DisplayName("특정 댄서가 수업에서 가장 많이 열었던 장르를 많은 순서부터 내림차순으로 정렬하여 반환한다.")
    @Test
    void findDistinctGenresByTeacherIdOrderByCountDesc() {
        TeacherJpaEntity teacher = createTeacher();
        lessonRepository.save(LessonFixture.create(teacher.getId(), 1, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(teacher.getId(), 1, Genre.HIPHOP, Level.BEGINNER));
        lessonRepository.save(LessonFixture.create(teacher.getId(), 1, Genre.FEMALE_HIPHOP, Level.BEGINNER));

        assertThat(lessonRepository.findDistinctGenresByTeacherIdOrderByCountDesc(1L)).containsExactly(Genre.HIPHOP, Genre.FEMALE_HIPHOP);
    }

    @DisplayName("수업을 저장할 때, 수업 이미지, 비디오, 라운드 정보도 함께 저장한다.")
    @Test
    void save() {
        // given
        TeacherJpaEntity teacher = createTeacher();
        Lesson lesson = LessonFixture.create(teacher.getId(), teacher.getMember().getId(), Genre.HIPHOP, Level.BEGINNER);

        // when
        lessonRepository.save(lesson);

        // then
        List<LessonImageJpaEntity> all = lessonImageJpaRepository.findAll();
        List<LessonRoundJpaEntity> all1 = lessonRoundJpaRepository.findAll();
        List<LessonVideoJpaEntity> all2 = lessonVideoJpaRepository.findAll();
        Assertions.assertAll(
                () -> assertThat(all.size()).isEqualTo(2),
                () -> assertThat(all1.size()).isEqualTo(1),
                () -> assertThat(all2.size()).isEqualTo(2)
        );
    }
}
