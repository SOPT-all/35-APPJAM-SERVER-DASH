package be.dash.dashserver.core.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.Role;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String JWT_CLAIM_ROLE = "role";
    private static final String JWT_IS_ACCESS_TOKEN = "isAccessToken";

    private final JwtProperties jwtProperties;
    private final KeyGenerator keyGenerator;

    String createAccessToken(String payload, Role role) {

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_CLAIM_ROLE, role);
        claims.put(JWT_IS_ACCESS_TOKEN, true);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.accessTokenValidTime()))
                .signWith(keyGenerator.getKeyFromString(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
                .compact();
    }

    String createRefreshToken(String payload, Role role) {

        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put(JWT_CLAIM_ROLE, role);
        claims.put(JWT_IS_ACCESS_TOKEN, true);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.refreshTokenValidTime()))
                .signWith(keyGenerator.getKeyFromString(jwtProperties.secretKey()), SignatureAlgorithm.HS256)
                .compact();
    }
}
