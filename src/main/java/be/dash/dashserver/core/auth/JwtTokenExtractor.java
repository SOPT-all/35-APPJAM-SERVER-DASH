package be.dash.dashserver.core.auth;

import org.springframework.stereotype.Component;
import be.dash.dashserver.core.domain.member.Role;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenExtractor {

    private static final String JWT_CLAIM_ROLE = "role";

    private final JwtProperties jwtProperties;
    private final KeyGenerator keyGenerator;

    public String getSubject(String token) {
        return Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Role getRole(String token) {
        String role = Jwts.parser()
                .setSigningKey(keyGenerator.getKeyFromString(jwtProperties.secretKey()))
                .parseClaimsJws(token)
                .getBody()
                .get(JWT_CLAIM_ROLE, String.class);

        return Role.valueOf(role);
    }
}
