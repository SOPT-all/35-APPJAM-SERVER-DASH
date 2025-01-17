package be.dash.dashserver.core.auth;

import org.springframework.stereotype.Service;
import be.dash.dashserver.core.domain.member.Role;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtTokenExtractor jwtTokenExtractor;
    private final TokenParser tokenParser;

    public Role getRole(String token) {
        return jwtTokenExtractor.getRole(tokenParser.getToken(token));
    }
}
