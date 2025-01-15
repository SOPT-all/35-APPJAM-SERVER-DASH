package be.dash.dashserver.core.auth;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(String refreshToken, long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteAllByMemberId(long memberId);
}
