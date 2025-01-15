package be.dash.dashserver.database.core.member;

import be.dash.dashserver.core.domain.member.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findBySocialIdAndProvider(String socialId, SocialProvider provider);
}
