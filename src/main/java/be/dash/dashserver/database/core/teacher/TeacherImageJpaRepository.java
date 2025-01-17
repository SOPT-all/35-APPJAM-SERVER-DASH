package be.dash.dashserver.database.core.teacher;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherImageJpaRepository extends JpaRepository<TeacherImageJpaEntity, Long> {
    List<TeacherImageJpaEntity> findAllByTeacherId(long teacherId);
}
