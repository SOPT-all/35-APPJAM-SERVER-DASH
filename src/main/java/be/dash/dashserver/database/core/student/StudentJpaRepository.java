package be.dash.dashserver.database.core.student;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {

    @Query("select distinct s from StudentJpaEntity s " +
            "join fetch s.member m " +
            "where m.id = :memberId")
    Optional<StudentJpaEntity> findStudentsByMemberIdWithMember(@Param("memberId") Long memberId);
}
