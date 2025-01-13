package be.dash.dashserver.database.core.genre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreJpaRepository extends JpaRepository<GenreJpaEntity, Long> {
}
