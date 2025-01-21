package be.dash.dashserver.database.core.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.domain.member.SocialProvider;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    Optional<MemberJpaEntity> findBySocialIdAndProvider(String socialId, SocialProvider provider);

    @Modifying
    @Query("update MemberJpaEntity m set m.role = :role where m.id = :id")
    void updateRole(Long id, Role role);
}
