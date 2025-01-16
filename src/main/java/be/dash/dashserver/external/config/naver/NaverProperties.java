package be.dash.dashserver.external.config.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "naver")
public record NaverProperties(
        String clientId,
        String clientSecret
) {
}
