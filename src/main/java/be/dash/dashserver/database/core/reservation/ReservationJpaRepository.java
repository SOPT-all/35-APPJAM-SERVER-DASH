package be.dash.dashserver.database.core.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
}
