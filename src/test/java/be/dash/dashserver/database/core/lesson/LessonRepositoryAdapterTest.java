package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
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
import be.dash.dashserver.database.core.member.MemberJpaRepositoy;
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
    private MemberJpaRepositoy memberJpaRepositoy;

    @BeforeEach
    void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @DisplayName("동적으로 필터에 해당하며, 마감기한이 지나지 않은 수업들을 조회한다.")
    @Test
    void findActiveLessonsByFilters() {
        LocalDateTime startDateTime = LocalDateTime.of(2025,1,15,3,40,50).minusDays(5);
        LocalDateTime endDateTime = LocalDateTime.of(2025,1,15,3,40,50).plusDays(10);
        createLessons(startDateTime, endDateTime);
        LocalDateTime now = LocalDateTime.of(2025,1,15,3,40,50);

        List<Lesson> lessonsHiphopBeginners = lessonRepository.findActiveLessonsByFilters(Genre.HIPHOP, Level.BEGINNER, startDateTime, endDateTime, now);
        List<Lesson> lessonsFemaleHiphopBeginners = lessonRepository.findActiveLessonsByFilters(Genre.FEMALE_HIPHOP, Level.BEGINNER, startDateTime, endDateTime, now);
        List<Lesson> lessonsFemaleHiphopAdvanced = lessonRepository.findActiveLessonsByFilters(Genre.HIPHOP, Level.ADVANCED, startDateTime, endDateTime, now);

        assertAll(
                () -> assertThat(lessonsHiphopBeginners.size()).isEqualTo(3),
                () -> assertThat(lessonsFemaleHiphopBeginners.size()).isEqualTo(1),
                () -> assertThat(lessonsFemaleHiphopAdvanced.size()).isEqualTo(1)
        );
    }

    private void createLessons(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        MemberJpaEntity memberJpaEntity = MemberJpaEntityFixture.create();
        memberJpaRepositoy.save(memberJpaEntity);
        TeacherJpaEntity teacherEntity = TeacherJpaEntityFixture.create(memberJpaEntity);
        teacherJpaRepository.save(teacherEntity);
        TeacherImageJpaEntity teacherImage = TeacherImageJpaEntityFixture.create(teacherEntity, "imageUrl");
        teacherImageJpaRepository.save(teacherImage);

        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(3), 10));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.FEMALE_HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.ADVANCED,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
    }
}
