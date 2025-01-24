package be.dash.dashserver.external.config.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@ConfigurationProperties(prefix = "toss")
public record TossProperties(String clientId,
                             String paymentUri,
                             String cancelUri,
                             long connectionTimeout,
                             long socketTimeout,
                             long readTimeout
                             ) {

}
