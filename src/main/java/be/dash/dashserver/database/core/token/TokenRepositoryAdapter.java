package be.dash.dashserver.database.core.token;

import be.dash.dashserver.core.auth.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRepositoryAdapter implements TokenRepository {

        private final TokenJpaRepository tokenJpaRepository;

        @Override
        public void save(String refreshToken, long memberId) {
            tokenJpaRepository.save(TokenJpaEntity.of(refreshToken, memberId));
        }
}
