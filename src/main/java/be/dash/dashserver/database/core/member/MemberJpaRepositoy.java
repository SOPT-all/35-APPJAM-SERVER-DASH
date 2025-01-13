package be.dash.dashserver.database.core.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepositoy extends JpaRepository<MemberJpaEntity, Long> {
}
