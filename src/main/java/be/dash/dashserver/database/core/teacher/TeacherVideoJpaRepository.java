package be.dash.dashserver.database.core.teacher;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherVideoJpaRepository extends JpaRepository<TeacherVideoJpaEntity, Long> {

    List<TeacherVideoJpaEntity> findAllByTeacherId(Long teacherId);
}
