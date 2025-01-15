package be.dash.dashserver.database.core.token;

import be.dash.dashserver.core.auth.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements TokenRepository {

        private final RefreshTokenJpaRepository refreshTokenJpaRepository;

        @Override
        public void save(String refreshToken, long memberId) {
            refreshTokenJpaRepository.save(RefreshTokenJpaEntity.of(refreshToken, memberId));
        }
}
