package be.dash.dashserver.database.core.lesson;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonImageJpaRepository extends JpaRepository<LessonImageJpaEntity, Long> {

    List<LessonImageJpaEntity> findAllByLesson_Id(Long lessonId);
}
