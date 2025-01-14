package be.dash.dashserver.core.auth;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackages = "be.dash.dashserver.core.auth")
public class JwtPropertiesConfiguration {
}
