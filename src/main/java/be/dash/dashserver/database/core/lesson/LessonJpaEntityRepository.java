package be.dash.dashserver.database.core.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LessonJpaEntityRepository extends JpaRepository<LessonJpaEntity, Long>, JpaSpecificationExecutor<LessonJpaEntity> {

}
