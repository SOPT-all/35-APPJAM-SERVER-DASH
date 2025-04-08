package be.dash.dashserver.database.core.token;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {
    Optional<RefreshTokenJpaEntity> findByRefreshToken(String refreshToken);

    @Modifying
    @Query("DELETE FROM RefreshTokenJpaEntity r WHERE r.memberId = :memberId")
    void deleteAllByMemberId(long memberId);

    boolean existsByMemberId(long memberId);

    @Modifying
    @Query("UPDATE RefreshTokenJpaEntity r SET r.refreshToken = :refreshToken WHERE r.id = :id")
    void update(String refreshToken, long id);
}
