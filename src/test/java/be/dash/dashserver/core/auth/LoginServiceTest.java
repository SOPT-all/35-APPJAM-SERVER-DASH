package be.dash.dashserver.core.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.auth.command.LoginCommand;
import be.dash.dashserver.core.auth.dto.KakaoAccount;
import be.dash.dashserver.core.auth.dto.KakaoProfile;
import be.dash.dashserver.core.auth.dto.LoginResult;
import be.dash.dashserver.core.auth.dto.OauthTokenResult;
import be.dash.dashserver.core.auth.dto.SocialInfoResult;
import be.dash.dashserver.core.domain.member.SocialProvider;
import be.dash.dashserver.database.core.token.RefreshTokenRepositoryAdapter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServiceTest extends ServiceSliceTest {
    @Autowired
    private LoginService loginService;
    @MockitoSpyBean
    private RefreshTokenRepositoryAdapter refreshTokenRepositoryAdapter;
    @MockitoBean
    private OauthClientApi oauthClientApi;

    @Test
    @DisplayName("외부 통신에 성공하면 토큰을 반환한다.")
    void login() {
        // Given
        when(oauthClientApi.getAccessToken(anyString(), anyString()))
                .thenReturn(new OauthTokenResult("accessToken"));
        when(oauthClientApi.getSocialUserInfo(anyString()))
                .thenReturn(new SocialInfoResult("id", new KakaoAccount("email", new KakaoProfile("nickname"))));

        // When
        LoginResult login = loginService.login(new LoginCommand(SocialProvider.KAKAO, "redirectUrl", "code"));

        // Then
        Assertions.assertThat(refreshTokenRepositoryAdapter.findByRefreshToken(login.refreshToken()).get().getRefreshToken())
                        .isEqualTo(login.refreshToken());
        Assertions.assertThat(login.accessToken()).isNotNull();
        Assertions.assertThat(login.refreshToken()).isNotNull();
        verify(refreshTokenRepositoryAdapter).save(anyString(), anyLong());

    }

    @Test
    @DisplayName("재로그인 시 리프레쉬 토큰을 업데이트한다.")
    void reLogin() throws InterruptedException {
        // Given
        when(oauthClientApi.getAccessToken(anyString(), anyString()))
                .thenReturn(new OauthTokenResult("accessToken"));

        when(oauthClientApi.getSocialUserInfo(anyString()))
                .thenReturn(new SocialInfoResult("id", new KakaoAccount("email", new KakaoProfile("nickname"))));
        LoginResult login = loginService.login(new LoginCommand(SocialProvider.KAKAO, "redirectUrl", "code"));
        // When
        LoginResult reLogin = loginService.login(new LoginCommand(SocialProvider.KAKAO, "redirectUrl", "code"));

        // Then
        verify(refreshTokenRepositoryAdapter).save(anyString(), anyLong());
        verify(refreshTokenRepositoryAdapter).update(anyString(), anyLong());

    }
}