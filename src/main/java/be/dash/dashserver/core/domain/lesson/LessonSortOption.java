package be.dash.dashserver.core.domain.lesson;

import java.util.Comparator;

public enum LessonSortOption {
    LATEST(Comparator.comparing(Lesson::getCreatedAt).reversed()),
    MOST_FAVORITE(Comparator.comparing(Lesson::getFavoriteCount).reversed()),
    UPCOMING(Comparator.comparing(Lesson::getStartTime));

    private final Comparator<Lesson> comparator;

    LessonSortOption(Comparator<Lesson> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Lesson> getComparator() {
        return comparator;
    }
}
