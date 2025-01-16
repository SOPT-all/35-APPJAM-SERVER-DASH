package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.core.fixture.LessonFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class LessonSortOptionTest {

    @Test
    void latest() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(LessonFixture.create(1, 1, 1, LocalDateTime.of(2025, 1, 1, 10, 0), 10L, LocalDateTime.now()
                .minusHours(1)));
        lessons.add(LessonFixture.create(2, 2, 2, LocalDateTime.of(2025, 1, 2, 10, 0), 20L, LocalDateTime.now()
                .minusHours(2)));
        lessons.add(LessonFixture.create(3, 3, 3, LocalDateTime.of(2024, 12, 31, 10, 0), 5L, LocalDateTime.now()
                .minusHours(3)));

        lessons.sort(LessonSortOption.LATEST.getComparator());

        assertAll(
                () -> assertThat(lessons.get(0).getId()).isEqualTo(1),
                () -> assertThat(lessons.get(1).getId()).isEqualTo(2),
                () -> assertThat(lessons.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    void most_favorite() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(LessonFixture.create(1, 1, 1, LocalDateTime.now(), 10L));
        lessons.add(LessonFixture.create(2, 2, 2, LocalDateTime.now(), 50L));
        lessons.add(LessonFixture.create(3, 3, 3, LocalDateTime.now(), 30L));

        lessons.sort(LessonSortOption.MOST_FAVORITE.getComparator());

        assertAll(
                () -> assertThat(lessons.get(0).getId()).isEqualTo(2),
                () -> assertThat(lessons.get(1).getId()).isEqualTo(3),
                () -> assertThat(lessons.get(2).getId()).isEqualTo(1)
        );
    }

    @Test
    void upcoming() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(LessonFixture.create(1, 1, 1, LocalDateTime.of(2025, 1, 3, 10, 0), 0L));
        lessons.add(LessonFixture.create(2, 2, 2, LocalDateTime.of(2025, 1, 1, 10, 0), 0L));
        lessons.add(LessonFixture.create(3, 3, 3, LocalDateTime.of(2025, 1, 2, 10, 0), 0L));

        lessons.sort(LessonSortOption.UPCOMING.getComparator());

        assertAll(
                () -> assertThat(lessons.get(0).getId()).isEqualTo(2),
                () -> assertThat(lessons.get(1).getId()).isEqualTo(3),
                () -> assertThat(lessons.get(2).getId()).isEqualTo(1)
        );
    }
}
