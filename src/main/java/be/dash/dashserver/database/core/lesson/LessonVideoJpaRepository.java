package be.dash.dashserver.database.core.lesson;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonVideoJpaRepository extends JpaRepository<LessonVideoJpaEntity, Long> {
    List<LessonVideoJpaEntity> findAllByLessonId(Long lessonId);
}
