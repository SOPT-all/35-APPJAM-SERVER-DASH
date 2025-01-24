package be.dash.dashserver.external.config.payment;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan(basePackages = "be.dash.dashserver.external.config.payment")
public class PayConfig {

    private final TossProperties tossProperties;

    @Bean
    public RestClient tossRestClient() {
        PoolingHttpClientConnectionManager connManager = getPoolingHttpClientConnectionManager();

        RequestConfig requestConfig = getRequestConfig();

        CloseableHttpClient httpClient = getHttpClient(connManager, requestConfig);

        return RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
                .baseUrl(tossProperties.paymentUri())
                .build();
    }

    private PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(tossProperties.connectionTimeout()))
                .setSocketTimeout(Timeout.ofSeconds(tossProperties.socketTimeout()))
                .build();

        return PoolingHttpClientConnectionManagerBuilder.create()
                .setDefaultConnectionConfig(connectionConfig)
                .build();
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setResponseTimeout(Timeout.ofSeconds(tossProperties.readTimeout()))
                .build();
    }

    private CloseableHttpClient getHttpClient(PoolingHttpClientConnectionManager connManager, RequestConfig requestConfig) {
        return HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
}
