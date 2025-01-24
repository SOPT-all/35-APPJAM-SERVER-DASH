package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import be.dash.dashserver.core.domain.common.Genre;

public interface LessonJpaEntityRepository extends JpaRepository<LessonJpaEntity, Long>, JpaSpecificationExecutor<LessonJpaEntity> {

    @Query("select l.genre " +
            "from LessonJpaEntity l " +
            "where l.teacher.id = :teacherId " +
            "group by l.genre " +
            "order by count(l) desc")
    List<Genre> findDistinctGenresByTeacherIdOrderByCountDesc(@Param("teacherId") Long teacherId);

    List<LessonJpaEntity> findByStartDateTimeGreaterThan(LocalDateTime endDateTime);

    @Query("select l.genre " +
            "from LessonJpaEntity l " +
            "where l.startDateTime > :now " +
            "group by l.genre " +
            "order by sum(l.reservationCount) desc")
    List<Genre> findPopularGenresByActiveLessons(@Param("now") LocalDateTime now);

    int countByTeacherId(Long teacherId);

    @Query("select l " +
            "from LessonJpaEntity l " +
            "where l.id in :lessonIds " +
            "order by l.startDateTime")
    List<LessonJpaEntity> findAllByIdsOOrderByStartDateTime(Set<Long> lessonIds);

    List<LessonJpaEntity> findByTeacherIdOrderByCreatedAtDesc(Long teacherId);

    @Query("select l " +
            "from LessonJpaEntity l " +
            "where l.teacher.id = :teacherId " +
            "order by l.startDateTime")
    List<LessonJpaEntity> findAllByTeacherIdOOrderByStartDateTime(Long teacherId);

    boolean existsByTeacherIdAndId(long teacherId, long lessonId);

    @Modifying
    @Query("update LessonJpaEntity l " +
            "set l.reservationCount = l.reservationCount + 1 " +
            "where l.id = :lessonId")
    void increaseReservationCount(long lessonId);
}
