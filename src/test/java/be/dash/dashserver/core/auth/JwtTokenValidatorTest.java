package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.domain.member.Role;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtTokenValidatorTest {

    private KeyGenerator keyGenerator = new KeyGenerator();
    private JwtProperties jwtProperties = new JwtProperties("secretasdfasdfasdfasdfasdlmlmllklklklfasdfasdfasdf",
            5000,
            5000);
    private JwtTokenValidator jwtTokenValidator = new JwtTokenValidator(keyGenerator, jwtProperties);

    @Test
    @DisplayName("잘못된 토큰에 대해 예외를 발생시켜야 한다.")
    void failValidateOnSignature() {
        // given
        Date now = new Date();
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + jwtProperties.accessTokenValidTime()))
                .signWith(keyGenerator.getKeyFromString(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
                .compact();
        // when & then
        Assertions.assertThatThrownBy(() ->jwtTokenValidator.validate(token + "a"))
                .isExactlyInstanceOf(UnAuthorizedException.class)
                .hasMessageContaining("잘못된 토큰");
    }

    @Test
    @DisplayName("만료된 토큰에 대해 예외를 발생시켜야 한다.")
    void failValidateOnExpiration() {
        // given
        Date now = new Date();
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() - jwtProperties.accessTokenValidTime()))
                .signWith(keyGenerator.getKeyFromString(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
                .compact();
        // when & then
        Assertions.assertThatThrownBy(() ->jwtTokenValidator.validate(token))
                .isExactlyInstanceOf(UnAuthorizedException.class)
                .hasMessageContaining("만료된 토큰");
    }

    @Test
    @DisplayName("토큰에서 subject를 가져올 수 있어야 한다.")
    void getSubject() {
        //given
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject("1")
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + jwtProperties.accessTokenValidTime()))
                .signWith(keyGenerator.getKeyFromString(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
                .compact();
        //when
        String subject = jwtTokenValidator.getSubject(token);
        //then
        Assertions.assertThat(subject).isEqualTo("1");
    }
}
