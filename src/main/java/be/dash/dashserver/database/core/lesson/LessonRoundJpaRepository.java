package be.dash.dashserver.database.core.lesson;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRoundJpaRepository extends JpaRepository<LessonRoundJpaEntity, Long> {
    List<LessonRoundJpaEntity> findAllByLessonIdOrderByStartTime(Long id);

    List<LessonRoundJpaEntity> findAllByLessonId(Long lessonId);
}
