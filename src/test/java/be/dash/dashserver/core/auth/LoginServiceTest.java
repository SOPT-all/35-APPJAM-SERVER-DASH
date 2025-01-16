package be.dash.dashserver.core.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import be.dash.dashserver.ServiceSliceTest;
import be.dash.dashserver.core.LoginResult;
import be.dash.dashserver.core.auth.command.LoginCommand;
import be.dash.dashserver.core.auth.dto.KakaoAccount;
import be.dash.dashserver.core.auth.dto.KakaoProfile;
import be.dash.dashserver.core.auth.dto.OauthTokenResult;
import be.dash.dashserver.core.auth.dto.SocialInfoResult;
import be.dash.dashserver.core.domain.member.SocialProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest extends ServiceSliceTest {
    @Autowired
    private LoginService loginService;
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
        LoginResult login = loginService.login(new LoginCommand(SocialProvider.KAKAO, "redirectUrl", "code"));

        Assertions.assertThat(login.accessToken()).isNotNull();
        Assertions.assertThat(login.refreshToken()).isNotNull();
    }
}