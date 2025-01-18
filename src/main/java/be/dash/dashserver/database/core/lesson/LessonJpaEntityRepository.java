package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.database.core.teacher.projection.TeacherLessonCount;

public interface LessonJpaEntityRepository extends JpaRepository<LessonJpaEntity, Long>, JpaSpecificationExecutor<LessonJpaEntity> {

    @Query("select new be.dash.dashserver.database.core.teacher.projection.TeacherLessonCount(t.id, t.member.nickname, count(l)) " +
            "from LessonJpaEntity l " +
            "join l.teacher t " +
            "group by t.member.nickname " +
            "order by count(l) desc")
    List<TeacherLessonCount> findTeacherLessonCountsDesc();

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
}
