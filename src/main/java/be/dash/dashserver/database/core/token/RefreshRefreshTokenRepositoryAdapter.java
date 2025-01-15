package be.dash.dashserver.database.core.token;

import be.dash.dashserver.core.auth.RefreshToken;
import be.dash.dashserver.core.auth.RefreshTokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshRefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public void save(String refreshToken, long memberId) {
        refreshTokenJpaRepository.save(RefreshTokenJpaEntity.from(refreshToken, memberId));
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenJpaRepository.findByRefreshToken(refreshToken)
                .map(RefreshTokenJpaEntity::toDomain);
    }

    @Override
    public void deleteAllByMemberId(long memberId) {
        refreshTokenJpaRepository.deleteAllByMemberId(memberId);
    }
}
