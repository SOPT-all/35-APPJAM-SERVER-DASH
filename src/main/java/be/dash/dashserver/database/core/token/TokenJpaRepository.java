package be.dash.dashserver.database.core.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<TokenJpaEntity, Long> {

}
