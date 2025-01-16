package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;

public class LessonSpecifications {

    private LessonSpecifications() {
    }

    public static Specification<LessonJpaEntity> findActiveLessonsByFilters(
            Genre genre,
            Level level,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            LocalDateTime now
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            equalGenre(genre, root, cb, predicates);
            equalLevel(level, root, cb, predicates);
            checkStartDateWithinRange(startDateTime, root, cb, predicates);
            checkEndDateWithinRange(endDateTime, root, cb, predicates);
            checkExpiredDate(now, root, cb, predicates);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void checkExpiredDate(LocalDateTime now, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (now != null) {
            predicates.add(cb.greaterThan(root.get("endDateTime"), now));
        }
    }

    private static void checkEndDateWithinRange(LocalDateTime endDateTime, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (endDateTime != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("endDateTime"), endDateTime));
        }
    }

    private static void checkStartDateWithinRange(LocalDateTime startDateTime, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (startDateTime != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDateTime"), startDateTime));
        }
    }

    private static void equalLevel(Level level, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (level != null) {
            predicates.add(cb.equal(root.get("level"), level));
        }
    }

    private static void equalGenre(Genre genre, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (genre != null) {
            predicates.add(cb.equal(root.get("genre"), genre));
        }
    }
}
