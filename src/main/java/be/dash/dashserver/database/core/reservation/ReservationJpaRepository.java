package be.dash.dashserver.database.core.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END " +
            "FROM ReservationJpaEntity r " +
            "JOIN r.student s " +
            "JOIN r.lesson l " +
            "WHERE s.member.id = :memberId AND l.id = :lessonId")
    boolean existsByMemberIdAndLessonId(@Param("memberId") Long memberId, @Param("lessonId") Long lessonId);
}
