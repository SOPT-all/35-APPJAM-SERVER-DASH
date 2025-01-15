package be.dash.dashserver.core.auth;

import org.springframework.stereotype.Component;
import be.dash.dashserver.core.exception.DashException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenParser {
    private static final String BEARER_PREFIX = "Bearer ";
    public String getToken(String token){
        if (token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        } else {
            throw new DashException("잘못된 토큰 형식입니다.");
        }
    }
}
