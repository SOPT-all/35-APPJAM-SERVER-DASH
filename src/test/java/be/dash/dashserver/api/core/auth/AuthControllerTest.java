package be.dash.dashserver.api.core.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import be.dash.dashserver.api.config.TestConfig;
import be.dash.dashserver.api.config.WebMvcConfig;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.LoginService;
import be.dash.dashserver.core.auth.LogoutService;
import be.dash.dashserver.core.auth.ReissueService;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.auth.command.LoginCommand;
import be.dash.dashserver.core.auth.dto.LoginResult;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = WebMvcConfig.class))
@Import(TestConfig.class)
class AuthControllerTest {
    @MockitoBean
    private LoginService loginService;
    @MockitoBean
    private ReissueService reissueService;
    @MockitoBean
    private LogoutService logoutService;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("주어진 로그인 요청을 처리하고 올바른 응답을 반환한다.")
    void login() throws Exception {
        LoginResult result = new LoginResult("at", "rt", true);
        when(loginService.login(any(LoginCommand.class))).thenReturn(result);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{\"provider\":\"KAKAO\",\"redirectUrl\":\"redirectUrl\",\"code\":\"code\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(result.accessToken()))
                .andExpect(jsonPath("$.refreshToken").value(result.refreshToken()))
                .andExpect(jsonPath("$.isOnboarded").value(result.isOnboarded()));
    }

    @Test
    @DisplayName("소셜 로그인 제공자를 제대로 입력하지 않으면 에러를 반환한다.")
    void failLogin() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType("application/json")
                        .content("{\"provider\":\"\",\"redirectUrl\":\"redirectUrl\",\"code\":\"code\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("refreshToken을 이용해 토큰을 재발급하고 올바른 응답을 반환한다.")
    void reissue() throws Exception {
        Token token = new Token("accessToken", "refreshToken");
        when(reissueService.reissue(any(String.class))).thenReturn(token);

        mockMvc.perform(post("/api/v1/auth/reissue")
                        .header("Authorization", "Bearer refreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }

    @Test
    @DisplayName("로그아웃 요청을 처리하고 올바른 응답을 반환한다.")
    void logout() throws Exception {
        doNothing().when(logoutService).logout(any(Long.class));

        mockMvc.perform(post("/api/v1/auth/logout")
                        .header("Authorization", "Bearer accessToken"))
                .andExpect(status().isNoContent());
    }
}
