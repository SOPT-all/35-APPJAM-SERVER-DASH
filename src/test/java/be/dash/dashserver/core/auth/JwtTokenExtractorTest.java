package be.dash.dashserver.core.auth;

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenExtractorTest {

    private static final String BEARER_PREFIX = "Bearer ";

    private final KeyGenerator keyGenerator = new KeyGenerator();
    private final JwtProperties jwtProperties = new JwtProperties("secretasdfasdfasdfasdfasdlmlmllklklklfasdfasdfasdf",
            5000,
            5000);
    private final TokenParser tokenParser = new TokenParser();
    private final JwtTokenExtractor jwtTokenExtractor = new JwtTokenExtractor(jwtProperties, keyGenerator, tokenParser);

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
        String subject = jwtTokenExtractor.getSubject(BEARER_PREFIX + token);
        //then
        Assertions.assertThat(subject).isEqualTo("1");
    }
}
