package be.dash.dashserver.database.core.reservation;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ReservationJpaEntity r " +
            "join StudentJpaEntity s on r.studentId = s.id " +
            "join LessonJpaEntity l on r.lessonId = l.id " +
            "WHERE s.member.id = :memberId AND l.id = :lessonId")
    boolean existsByMemberIdAndLessonId(@Param("memberId") Long memberId, @Param("lessonId") Long lessonId);

    int countByStudentId(Long studentId);

    List<ReservationJpaEntity> findAllByStudentId(long studentId);

    List<ReservationJpaEntity> findAllByLessonIdOrderByCreatedAtDesc(Long lessonId);
}
