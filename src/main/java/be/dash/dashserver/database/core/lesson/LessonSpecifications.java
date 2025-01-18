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
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDateTime"), now));
        }
    }

    private static void checkStartDateWithinRange(LocalDateTime filterStartDateTime, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filterStartDateTime != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDateTime"), filterStartDateTime));
        }
    }

    private static void checkEndDateWithinRange(LocalDateTime filterEndDateTime, Root<LessonJpaEntity> root, CriteriaBuilder cb, List<Predicate> predicates) {
        if (filterEndDateTime != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("startDateTime"), filterEndDateTime));
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

    public static Specification<LessonJpaEntity> findActiveLessonsByGenreOrLevel(
            LocalDateTime now,
            List<Genre> genres,
            List<Level> levels
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate genrePredicate = null;
            genrePredicate = checkContainGenres(genres, root, genrePredicate);
            Predicate levelPredicate = null;
            levelPredicate = checkContainLevels(levels, root, levelPredicate);

            Predicate genreOrLevel = null;
            if (allMatch(genrePredicate, levelPredicate)) {
                genreOrLevel = cb.or(genrePredicate, levelPredicate);
            } else if (genreMatch(genrePredicate)) {
                genreOrLevel = genrePredicate;
            } else if (levelMatch(levelPredicate)) {
                genreOrLevel = levelPredicate;
            }

            if (anyMatch(genreOrLevel)) {
                predicates.add(genreOrLevel);
            }
            checkExpiredDate(now, root, cb, predicates);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate checkContainGenres(List<Genre> genres, Root<LessonJpaEntity> root, Predicate genrePredicate) {
        if (genres != null && !genres.isEmpty()) {
            genrePredicate = root.get("genre").in(genres);
        }
        return genrePredicate;
    }

    private static Predicate checkContainLevels(List<Level> levels, Root<LessonJpaEntity> root, Predicate levelPredicate) {
        if (levels != null && !levels.isEmpty()) {
            levelPredicate = root.get("level").in(levels);
        }
        return levelPredicate;
    }

    private static boolean allMatch(Predicate genrePredicate, Predicate levelPredicate) {
        return genrePredicate != null && levelPredicate != null;
    }

    private static boolean genreMatch(Predicate genrePredicate) {
        return genrePredicate != null;
    }

    private static boolean levelMatch(Predicate levelPredicate) {
        return levelPredicate != null;
    }

    private static boolean anyMatch(Predicate genreOrLevel) {
        return genreOrLevel != null;
    }
}
