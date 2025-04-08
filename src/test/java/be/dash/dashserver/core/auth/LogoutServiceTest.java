package be.dash.dashserver.core.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import be.dash.dashserver.ServiceSliceTest;

class LogoutServiceTest extends ServiceSliceTest {

    @Autowired
    private LogoutService logoutService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @DisplayName("로그아웃 유저의 refreshToken을 삭제한다.")
    @Test
    void logout() {
        // Given
        refreshTokenRepository.save("refreshToken", 1L);
        // When
        logoutService.logout(1L);
        // Then
        Assertions.assertThat(refreshTokenRepository.existsByMemberId(1L)).isFalse();
    }
}
