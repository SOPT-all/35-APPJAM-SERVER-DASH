package be.dash.dashserver.core.auth;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JwtTokenExtractorTest {

    private final KeyGenerator keyGenerator = new KeyGenerator();
    private final JwtProperties jwtProperties = new JwtProperties("secretasdfasdfasdfasdfasdlmlmllklklklfasdfasdfasdf",
            5000,
            5000);
    private final JwtTokenExtractor jwtTokenExtractor = new JwtTokenExtractor(jwtProperties, keyGenerator);

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
        String subject = jwtTokenExtractor.getSubject(token);
        //then
        Assertions.assertThat(subject).isEqualTo("1");
    }
}
