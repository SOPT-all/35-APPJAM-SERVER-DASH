package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.domain.member.Role;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    private final JwtProperties jwtProperties;
    private final KeyGenerator keyGenerator;
    private final TokenParser tokenParser;

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                .parseClaimsJws(tokenParser.getToken(token))
                .getBody()
                .getSubject();
    }

    public Role getRole(String token) {
        String role = Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                .parseClaimsJws(tokenParser.getToken(token))
                .getBody()
                .get("role", String.class);

        return Role.valueOf(role);
    }

}
