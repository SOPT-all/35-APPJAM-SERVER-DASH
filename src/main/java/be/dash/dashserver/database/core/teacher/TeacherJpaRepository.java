package be.dash.dashserver.database.core.teacher;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherJpaRepository extends JpaRepository<TeacherJpaEntity, Long> {
    Optional<TeacherJpaEntity> findByMemberId(Long memberId);
}
