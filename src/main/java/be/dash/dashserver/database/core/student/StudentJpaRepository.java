package be.dash.dashserver.database.core.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, Long> {
}
