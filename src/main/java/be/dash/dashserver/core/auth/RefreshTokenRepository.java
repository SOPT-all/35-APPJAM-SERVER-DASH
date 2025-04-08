package be.dash.dashserver.core.auth;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(String refreshToken, long memberId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    boolean existsByMemberId(long memberId);

    void deleteAllByMemberId(long memberId);

    void update(String refreshToken, long id);
}
