package be.dash.dashserver.core.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        int accessTokenValidTime,
        int refreshTokenValidTime) {
}
