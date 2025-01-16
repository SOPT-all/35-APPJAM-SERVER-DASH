package be.dash.dashserver.core.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenParserTest {

    @DisplayName("토큰이 정상적으로 파싱된다.")
    @Test
    void getToken() {
        // given
        TokenParser tokenParser = new TokenParser();
        String token = "Bearer token";
        // when
        String result = tokenParser.getToken(token);
        // then
        assertThat(result).isEqualTo("token");
    }

    @DisplayName("잘못된 토큰 형식이 들어오면 예외가 발생한다.")
    @Test
    void failGetToken() {
        // given
        TokenParser tokenParser = new TokenParser();
        String token = "Bear token";
        // when, then
        assertThatThrownBy(() -> tokenParser.getToken(token))
                .isInstanceOf(UnAuthorizedException.class)
                .hasMessage("잘못된 토큰 형식입니다.");
    }
}
