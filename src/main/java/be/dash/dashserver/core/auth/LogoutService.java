package be.dash.dashserver.core.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void logout(long memberId) {
        refreshTokenRepository.deleteAllByMemberId(memberId);
    }
}

