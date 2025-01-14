package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.domain.member.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtTokenProviderTest {
    private KeyGenerator keyGenerator = new KeyGenerator();
    private JwtProperties jwtProperties = new JwtProperties("secretasdfasdfasdfasdfasdlmlmllklklklfasdfasdfasdf",
            5000,
            5000);
    private JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(jwtProperties, keyGenerator);

    @Test
    @DisplayName("Access Token을 생성할 수 있다.")
    void createAccessToken() {
        // given
        String payload = "1";
        Role role = Role.MEMBER;
        // when
        String token = jwtTokenProvider.createAccessToken(payload, Role.MEMBER);
        // then
        Assertions.assertThat(token).isNotNull();
    }

    @Test()
    @DisplayName("Access Token을 생성할 수 있다.")
    void createRefreshToken() {
        // given
        String payload = "1";
        Role role = Role.MEMBER;
        // when
        String token = jwtTokenProvider.createRefreshToken(payload, Role.MEMBER);
        // then
        Assertions.assertThat(token).isNotNull();
    }

}
