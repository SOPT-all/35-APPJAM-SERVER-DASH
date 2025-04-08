package be.dash.dashserver.core.auth;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void logout(long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }
}

