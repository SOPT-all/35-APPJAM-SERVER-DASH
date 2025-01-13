package be.dash.dashserver.database.core.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteJpaEntity, Long> {
}
