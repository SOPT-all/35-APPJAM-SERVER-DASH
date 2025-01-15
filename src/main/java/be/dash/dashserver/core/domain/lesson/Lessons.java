package be.dash.dashserver.core.domain.lesson;

import java.util.List;
import java.util.stream.Collectors;
import be.dash.dashserver.core.domain.common.SortOption;

public record Lessons(List<Lesson> lessons) {

    public Lessons sort(SortOption sortOption) {
        return new Lessons(lessons.stream()
                .sorted(sortOption.getComparator())
                .collect(Collectors.toList()));
    }
}
