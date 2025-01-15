package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.common.SortOption;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.member.Member;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherImageRepository;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.fixture.LessonFixture;
import be.dash.dashserver.core.fixture.MemberFixture;
import be.dash.dashserver.core.fixture.TeacherFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LessonServiceTest extends ServiceSliceTest {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherImageRepository teacherImageRepository;

    @DisplayName("동적으로 필터에 해당하며, 마감기한이 지나지 않은 수업들을 정렬 조건에 맞게 정렬 후 조회한다.")
    @Test
    void search() {
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(10);
        createLessons(startDateTime, endDateTime);

        Lessons lessonsLatest = lessonService.search(Genre.HIPHOP, Level.BEGINNER, startDateTime, endDateTime, SortOption.LATEST);
        Lessons lessonsMostFavorite = lessonService.search(Genre.HIPHOP, Level.BEGINNER, startDateTime, endDateTime, SortOption.MOST_FAVORITE);
        Lessons lessonsUpComing = lessonService.search(Genre.HIPHOP, Level.BEGINNER, startDateTime, endDateTime, SortOption.UPCOMING);

        assertAll(
                () -> assertThat(lessonsLatest.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(5L, 4L, 3L),
                () -> assertThat(lessonsMostFavorite.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(3L, 4L, 5L),
                () -> assertThat(lessonsUpComing.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(3L, 5L, 4L)
        );
    }

    private void createLessons(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Member memberWithoutId = MemberFixture.createTeacherWithoutId();
        memberRepository.save(memberWithoutId);
        Teacher teacherWithoutId = TeacherFixture.createWithoutId(1);
        teacherRepository.save(teacherWithoutId);
        Teacher teacher = TeacherFixture.create(1, 1);
        teacherImageRepository.save(teacher);

        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(3), 10));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.FEMALE_HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
    }
}
