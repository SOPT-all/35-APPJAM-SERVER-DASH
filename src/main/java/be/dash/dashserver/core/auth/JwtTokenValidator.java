package be.dash.dashserver.core.auth;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenValidator {

    private final KeyGenerator keyGenerator;
    private final JwtProperties jwtProperties;

    public void validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw UnAuthorizedException.wrong(token);
        } catch (ExpiredJwtException e) {
            throw UnAuthorizedException.expired(token);
        }
    }

    public String getSubject(String token) {

        return Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
