package be.dash.dashserver.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secretKey;
    private int accessTokenValidTime;
    private int refreshTokenValidTime;

}
