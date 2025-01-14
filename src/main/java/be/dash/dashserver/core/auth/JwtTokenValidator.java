package be.dash.dashserver.core.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenValidator {

    private final KeyGenerator keyGenerator;
    private final JwtProperties jwtProperties;

    public void validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.getSecretKey()))
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw new UnAuthorizedException("잘못된 토큰입니다.");
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException("만료된 토큰입니다.");
        }
    }

    public String getSubject(String token) {

        return Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.getSecretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
