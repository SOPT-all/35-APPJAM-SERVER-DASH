package be.dash.dashserver.database.core.teacher;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import be.dash.dashserver.database.core.teacher.projection.TeacherLessonCount;

public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, Long> {
    Optional<TeacherJpaEntity> findByMemberId(Long memberId);

    @Query("select new be.dash.dashserver.database.core.teacher.projection.TeacherLessonCount(t.id, t.member.nickname, count(l)) " +
            "from TeacherJpaEntity t " +
            "left join LessonJpaEntity l on l.teacher.id = t.id " +
            "where t.member.nickname like %:keyword% " +
            "group by t.id, t.member.nickname " +
            "order by count(l) desc")
    List<TeacherLessonCount> findTeacherLessonCountsDesc(@Param("keyword") String keyword);
}
