package be.dash.dashserver.database.core.teacher;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherImageJpaRepository extends JpaRepository<TeacherImageJpaEntity, Long> {

    Optional<TeacherImageJpaEntity> findFirstByTeacherId(Long teacherId);
}
