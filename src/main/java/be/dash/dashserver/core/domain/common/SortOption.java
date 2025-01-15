package be.dash.dashserver.core.domain.common;

import java.util.Comparator;
import be.dash.dashserver.core.domain.lesson.Lesson;

public enum SortOption {
    LATEST(Comparator.comparing(Lesson::getCreatedAt).reversed()),
    MOST_FAVORITE(Comparator.comparing(Lesson::getFavoriteCount).reversed()),
    UPCOMING(Comparator.comparing(Lesson::getStartDateTime));

    private final Comparator<Lesson> comparator;

    SortOption(Comparator<Lesson> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Lesson> getComparator() {
        return comparator;
    }
}
