package be.dash.dashserver.core.domain.lesson;

import java.util.List;
import java.util.stream.Collectors;

public record Lessons(List<Lesson> lessons) {

    public Lessons sort(LessonSortOption sortOption) {
        return new Lessons(lessons.stream()
                .sorted(sortOption.getComparator())
                .collect(Collectors.toList()));
    }
}
