package be.dash.dashserver.external.naver;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import be.dash.dashserver.core.domain.lesson.Locations;
import be.dash.dashserver.core.external.LocationSearchClientApi;
import be.dash.dashserver.external.config.naver.NaverProperties;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverClientCaller implements LocationSearchClientApi {
    private static final String SCHEME = "https";
    private static final String BASE_URL = "openapi.naver.com";
    private static final String PATH = "/v1/search/local.json";
    private static final int display = 5;

    private final RestClient restClient;
    private final NaverProperties naverProperties;

    @Override
    public Locations getLocations(String query) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(SCHEME)
                        .host(BASE_URL)
                        .path(PATH)
                        .queryParam("query", query)
                        .queryParam("display", display)
                        .build())
                .header("X-Naver-Client-Id", naverProperties.clientId())
                .header("X-Naver-Client-Secret", naverProperties.clientSecret())
                .retrieve()
                .toEntity(Locations.class)
                .getBody();
    }
}
