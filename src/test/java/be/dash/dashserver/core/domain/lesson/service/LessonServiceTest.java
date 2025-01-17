package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
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
import static be.dash.dashserver.core.domain.common.Genre.CHOREOGRAPHY;
import static be.dash.dashserver.core.domain.common.Genre.FEMALE_HIPHOP;
import static be.dash.dashserver.core.domain.common.Genre.HIPHOP;
import static be.dash.dashserver.core.domain.common.Genre.HOUSE;
import static be.dash.dashserver.core.domain.common.Genre.KPOP;

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

        Lessons lessonsLatest = lessonService.search(HIPHOP, Level.BEGINNER, startDateTime, endDateTime, LessonSortOption.LATEST);
        Lessons lessonsMostFavorite = lessonService.search(HIPHOP, Level.BEGINNER, startDateTime, endDateTime, LessonSortOption.MOST_FAVORITE);
        Lessons lessonsUpComing = lessonService.search(HIPHOP, Level.BEGINNER, startDateTime, endDateTime, LessonSortOption.UPCOMING);

        assertAll(
                () -> assertThat(lessonsLatest.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(6L, 5L, 4L),
                () -> assertThat(lessonsMostFavorite.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(4L, 5L, 6L),
                () -> assertThat(lessonsUpComing.lessons().stream().map(Lesson::getId)
                        .toList()).containsExactly(4L, 6L, 5L)
        );
    }

    private void createLessons(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Member memberWithoutId = MemberFixture.createTeacherWithoutId();
        memberRepository.save(memberWithoutId);
        Teacher teacherWithoutId = TeacherFixture.createWithoutId(1);
        teacherRepository.save(teacherWithoutId);
        Teacher teacher = TeacherFixture.create(1, 1);
        teacherImageRepository.saveAll(teacher);

        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(15), 10));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(3), 10));
        lessonRepository.save(LessonFixture.create(1, 1, FEMALE_HIPHOP, Level.BEGINNER,
                startDateTime, endDateTime, 10));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
    }

    @DisplayName("회원의 경우 회원이 관심있어 하는 장르와 난이도에 따라 수업을 추천한다.")
    @Test
    void getRecommendationLessonsMember() {
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(10);
        createRecommendationLessons(startDateTime, endDateTime);
        Member studentWithoutId = MemberFixture.createStudentWithoutId("nickname", Genre.POPPING, Level.BEGINNER, "010-3333-2222");
        Member member = memberRepository.save(studentWithoutId);
        memberRepository.onboard(member);

        Lessons recommendationLessons = lessonService.getRecommendationLessons(member.getId());

        assertAll(
                () -> assertThat(recommendationLessons.lessons().size()).isEqualTo(3),
                () -> assertThat(recommendationLessons.lessons().get(0).getGenre()).isEqualTo(FEMALE_HIPHOP),
                () -> assertThat(recommendationLessons.lessons().get(0).getLevel()).isEqualTo(Level.BEGINNER),
                () -> assertThat(recommendationLessons.lessons().get(1).getGenre()).isEqualTo(HIPHOP),
                () -> assertThat(recommendationLessons.lessons().get(1).getLevel()).isEqualTo(Level.BEGINNER),
                () -> assertThat(recommendationLessons.lessons().get(2).getGenre()).isEqualTo(Genre.POPPING),
                () -> assertThat(recommendationLessons.lessons().get(2).getLevel()).isEqualTo(Level.NOVICE)
        );
    }

    private void createRecommendationLessons(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Member memberWithoutId = MemberFixture.createTeacherWithoutId();
        memberRepository.save(memberWithoutId);
        Teacher teacherWithoutId = TeacherFixture.createWithoutId(1);
        teacherRepository.save(teacherWithoutId);
        Teacher teacher = TeacherFixture.create(1, 1);
        teacherImageRepository.saveAll(teacher);

        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.minusDays(5), endDateTime.minusDays(15), 10));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.INTERMEDIATE,
                startDateTime.plusDays(1), endDateTime.minusDays(1), 50));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.POPPING, Level.NOVICE,
                startDateTime.plusDays(3), endDateTime.minusDays(1), 40));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
        lessonRepository.save(LessonFixture.create(1, 1, FEMALE_HIPHOP, Level.BEGINNER,
                startDateTime.plusDays(2), endDateTime.minusDays(1), 30));
        lessonRepository.save(LessonFixture.create(1, 1, Genre.POPPING, Level.BEGINNER,
                startDateTime.minusDays(10), endDateTime.minusDays(15), 40));
    }

    @DisplayName("예약이 가장 많은 순서대로 장르를 반환한다.")
    @Test
    void getPopularGenres() {
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(10);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(10);
        createPopularGenreLessons(startDateTime, endDateTime);

        List<Genre> popularGenres = lessonService.getPopularGenres();
        assertAll(
                () -> assertThat(popularGenres.size()).isEqualTo(4),
                () -> assertThat(popularGenres.get(0)).isEqualTo(FEMALE_HIPHOP),
                () -> assertThat(popularGenres.get(1)).isEqualTo(KPOP),
                () -> assertThat(popularGenres.get(2)).isEqualTo(CHOREOGRAPHY),
                () -> assertThat(popularGenres.get(3)).isEqualTo(HIPHOP)
        );
    }

    private void createPopularGenreLessons(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Member memberWithoutId = MemberFixture.createTeacherWithoutId();
        memberRepository.save(memberWithoutId);
        Teacher teacherWithoutId = TeacherFixture.createWithoutId(1);
        teacherRepository.save(teacherWithoutId);
        Teacher teacher = TeacherFixture.create(1, 1);
        teacherImageRepository.saveAll(teacher);

        lessonRepository.save(LessonFixture.create(1, 1, HOUSE, startDateTime.minusDays(5), endDateTime.minusDays(15), 1));
        lessonRepository.save(LessonFixture.create(1, 1, HIPHOP, startDateTime.minusDays(5), endDateTime.minusDays(3), 1));
        lessonRepository.save(LessonFixture.create(1, 1, FEMALE_HIPHOP, startDateTime.minusDays(5), endDateTime.minusDays(3), 10));
        lessonRepository.save(LessonFixture.create(1, 1, FEMALE_HIPHOP, startDateTime, endDateTime, 50));
        lessonRepository.save(LessonFixture.create(1, 1, KPOP, startDateTime.plusDays(1), endDateTime.minusDays(1), 5));
        lessonRepository.save(LessonFixture.create(1, 1, KPOP, startDateTime.plusDays(1), endDateTime.minusDays(1), 5));
        lessonRepository.save(LessonFixture.create(1, 1, KPOP, startDateTime.plusDays(1), endDateTime.minusDays(1), 5));
        lessonRepository.save(LessonFixture.create(1, 1, KPOP, startDateTime.plusDays(1), endDateTime.minusDays(1), 5));
        lessonRepository.save(LessonFixture.create(1, 1, CHOREOGRAPHY, startDateTime.plusDays(1), endDateTime.minusDays(1), 3));
        lessonRepository.save(LessonFixture.create(1, 1, CHOREOGRAPHY, startDateTime.plusDays(3), endDateTime.minusDays(1), 3));
        lessonRepository.save(LessonFixture.create(1, 1, CHOREOGRAPHY, startDateTime.plusDays(2), endDateTime.minusDays(1), 3));
    }
}
